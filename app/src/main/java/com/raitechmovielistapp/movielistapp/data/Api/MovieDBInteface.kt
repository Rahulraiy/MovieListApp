package com.raitechmovielistapp.movielistapp.data.Api

import com.raitechmovielistapp.movielistapp.data.Vo.MovieTopRatedResponse
import com.raitechmovielistapp.movielistapp.data.Vo.MovieUpcomingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBInteface {

    //https://api.themoviedb.org/3/movie/top_rated?api_key=3038b37245c36bb4eecdb18cdfa9bb79
    //https://api.themoviedb.org/3/movie/upcoming?api_key=3038b37245c36bb4eecdb18cdfa9bb79

    @GET("movie/top_rated")
    fun getTopratedMovie(@Query("page")page:Int):Single<MovieTopRatedResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovie(@Query("page")page:Int):Single<MovieUpcomingResponse>
}