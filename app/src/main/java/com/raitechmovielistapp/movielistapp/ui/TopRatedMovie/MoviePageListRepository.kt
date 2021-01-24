package com.raitechmovielistapp.movielistapp.ui.TopRatedMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.raitechmovielistapp.movielistapp.data.Api.MovieDBInteface
import com.raitechmovielistapp.movielistapp.data.Api.POST_PER_PAGE
import com.raitechmovielistapp.movielistapp.data.Repository.MovieDataSourceFactory
import com.raitechmovielistapp.movielistapp.data.Repository.MovieNetworkDataSource
import com.raitechmovielistapp.movielistapp.data.Repository.NetworkState
import com.raitechmovielistapp.movielistapp.data.Vo.MovieTopRated
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository (private val apiservice:MovieDBInteface){


    lateinit var moviePagedlist:LiveData<PagedList<MovieTopRated>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable):LiveData<PagedList<MovieTopRated>>{

        movieDataSourceFactory= MovieDataSourceFactory(apiservice,compositeDisposable)

        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedlist=LivePagedListBuilder(movieDataSourceFactory,config).build()

        return moviePagedlist
    }

    fun getNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap<MovieNetworkDataSource,NetworkState>(
            movieDataSourceFactory.movieLiveDataSource,MovieNetworkDataSource::networkState)

    }
}