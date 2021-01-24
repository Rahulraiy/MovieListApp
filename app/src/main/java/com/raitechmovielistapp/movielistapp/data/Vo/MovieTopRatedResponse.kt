package com.raitechmovielistapp.movielistapp.data.Vo


import com.google.gson.annotations.SerializedName

data class MovieTopRatedResponse(
    val page: Int,
    @SerializedName("results")
    val movieList: List<MovieTopRated>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)