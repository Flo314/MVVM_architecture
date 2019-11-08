package com.example.mvvm_architecture.data.data.repository

import androidx.lifecycle.LiveData
import com.example.mvvm_architecture.data.NetworkState
import com.example.mvvm_architecture.data.data.api.ITheMovieDb
import com.example.mvvm_architecture.data.data.models.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: ITheMovieDb) {

    lateinit var movieDetailsDataSource: MovieDetailNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsDataSource = MovieDetailNetworkDataSource(apiService, compositeDisposable)
        movieDetailsDataSource.fetchMovieDetail(movieId)

        return movieDetailsDataSource.downlodedMovieDetailsResponse
    }

    // obtenir les données d'état du réseau
    fun getMovieDetailsNetworkDataSource(): LiveData<NetworkState> {
       return movieDetailsDataSource.networkState

    }
}