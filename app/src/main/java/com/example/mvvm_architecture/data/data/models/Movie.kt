package com.example.mvvm_architecture.data.data.models

import com.google.gson.annotations.SerializedName

data class Result (

    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releasedate: String,
    val title: String

)
