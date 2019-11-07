package com.example.mvvm_architecture.single_movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_architecture.data.NetworkState
import com.example.mvvm_architecture.data.data.models.MovieDetails
import com.example.mvvm_architecture.data.data.repository.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository: MovieDetailsRepository, movieId: Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkDataSource()
    }

    // appel quand l'activity ou le fragment est détruit
    // permet de ne pas avoir de fuite de mémoire
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}