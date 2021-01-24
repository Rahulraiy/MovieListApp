package com.raitechmovielistapp.movielistapp.data.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.raitechmovielistapp.movielistapp.data.Api.MovieDBInteface
import com.raitechmovielistapp.movielistapp.data.Vo.MovieTopRated
import com.raitechmovielistapp.movielistapp.data.Vo.MovieUpComing
import io.reactivex.disposables.CompositeDisposable

class UpComingMovieDataSourceFactory (private val apiService: MovieDBInteface, private val compositeDisposable: CompositeDisposable):
    DataSource.Factory<Int, MovieUpComing>() {
    val movieupcomingLiveDataSource= MutableLiveData<MovieUpComingNetworkDataSource>()


    override fun create(): DataSource<Int, MovieUpComing> {

        val movieupcomingDataSource=MovieUpComingNetworkDataSource(apiService,compositeDisposable)
        movieupcomingLiveDataSource.postValue(movieupcomingDataSource)
        return movieupcomingDataSource

    }
}