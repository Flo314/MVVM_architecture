package com.example.mvvm_architecture.data.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mvvm_architecture.data.NetworkState
import com.example.mvvm_architecture.data.data.api.FIRST_PAGE
import com.example.mvvm_architecture.data.data.api.ITheMovieDb
import com.example.mvvm_architecture.data.data.models.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Utilisation de PageKeyedDataSource car chargement des données sur le numéro de page
 */
class MovieDataSource(
    private val apiService: ITheMovieDb,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>,callback: LoadInitialCallback<Int, Movie> ) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.movieList, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDatasource", it.message)
                    }
                )
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages >= params.key){

                        callback.onResult(it.movieList,params.key + 1)
                        networkState.postValue(NetworkState.LOADED)

                    } else {
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }
                },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDatasource", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}