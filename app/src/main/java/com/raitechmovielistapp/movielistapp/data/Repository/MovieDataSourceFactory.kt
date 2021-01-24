package com.raitechmovielistapp.movielistapp.data.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.raitechmovielistapp.movielistapp.data.Api.MovieDBInteface
import com.raitechmovielistapp.movielistapp.data.Vo.MovieTopRated
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: MovieDBInteface, private val compositeDisposable: CompositeDisposable):DataSource.Factory<Int,MovieTopRated>() {

    val movieLiveDataSource=MutableLiveData<MovieNetworkDataSource>()

    override fun create(): DataSource<Int, MovieTopRated> {

        val movieDataSource=MovieNetworkDataSource(apiService,compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}