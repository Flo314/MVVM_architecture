package com.example.mvvm_architecture.data.data.api

import com.example.mvvm_architecture.data.data.models.MovieDetails
import com.example.mvvm_architecture.data.data.models.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ITheMovieDb {

    // Single (rxjava2) est un d'observable qui emet des datas

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int) : Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int) : Single<MovieDetails>
}