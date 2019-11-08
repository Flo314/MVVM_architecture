package com.example.mvvm_architecture.data.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_architecture.data.NetworkState
import com.example.mvvm_architecture.data.data.api.ITheMovieDb
import com.example.mvvm_architecture.data.data.models.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Appel de l'api avec Rxjava
 * compositeDisposable est un composant rxjava qui permet de faire des appels à l'api
 * dans un thread
 */
class MovieDetailNetworkDataSource(private val apiService : ITheMovieDb,
                                   private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    // retourne le film avec les données
    private val _downlodedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downlodedMovieDetailsResponse: LiveData<MovieDetails>
        get() = _downlodedMovieDetailsResponse

    fun fetchMovieDetail(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            // appel réseau
            compositeDisposable.add(apiService.getMovieDetails(movieId)
                // pour l'observer on s'abonne sur le thread Schedulers
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _downlodedMovieDetailsResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.LOADED)
                        Log.e("MovieDetailDataSource", it.message)
                    }
                )
            )

        }catch (e: Exception) {
            Log.e("MovieDetailDataSource", e.message)
        }
    }


}