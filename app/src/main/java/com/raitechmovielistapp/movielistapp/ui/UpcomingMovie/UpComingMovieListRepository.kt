package com.raitechmovielistapp.movielistapp.ui.UpcomingMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.raitechmovielistapp.movielistapp.data.Api.MovieDBInteface
import com.raitechmovielistapp.movielistapp.data.Api.POST_PER_PAGE
import com.raitechmovielistapp.movielistapp.data.Repository.*
import com.raitechmovielistapp.movielistapp.data.Vo.MovieUpComing
import io.reactivex.disposables.CompositeDisposable

class UpComingMovieListRepository (private val apiservice: MovieDBInteface) {


    lateinit var movieupcomingPagedlist: LiveData<PagedList<MovieUpComing>>
    lateinit var upcomingmovieDataSourceFactory: UpComingMovieDataSourceFactory

    fun fetchLiveMovieupcomingPagedList(compositeDisposable: CompositeDisposable):LiveData<PagedList<MovieUpComing>>{

        upcomingmovieDataSourceFactory= UpComingMovieDataSourceFactory(apiservice,compositeDisposable)

        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        movieupcomingPagedlist= LivePagedListBuilder(upcomingmovieDataSourceFactory,config).build()

        return movieupcomingPagedlist
    }

    fun getNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap<MovieUpComingNetworkDataSource, NetworkState>(
            upcomingmovieDataSourceFactory.movieupcomingLiveDataSource, MovieUpComingNetworkDataSource::networkState)

    }

}