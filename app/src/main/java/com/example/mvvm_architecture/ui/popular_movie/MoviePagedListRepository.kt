package com.example.mvvm_architecture.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mvvm_architecture.data.NetworkState
import com.example.mvvm_architecture.data.data.api.ITheMovieDb
import com.example.mvvm_architecture.data.data.api.POST_PER_PAGE
import com.example.mvvm_architecture.data.data.models.Movie
import com.example.mvvm_architecture.data.data.repository.MovieDataSource
import com.example.mvvm_architecture.data.data.repository.MovieDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiService: ITheMovieDb) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>>{

        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState() : LiveData<NetworkState>{

        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}