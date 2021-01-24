package com.raitechmovielistapp.movielistapp.data.Vo


import com.google.gson.annotations.SerializedName

data class MovieUpComing(

    @SerializedName("id")
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String,

    val title: String,


)