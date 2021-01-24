package com.raitechmovielistapp.movielistapp.data.Vo


import com.google.gson.annotations.SerializedName

data class MovieUpcomingResponse(
    val page: Int,
    @SerializedName("results")
    val movieUpComingList: List<MovieUpComing>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)