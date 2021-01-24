package com.raitechmovielistapp.movielistapp.ui.TopRatedMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.raitechmovielistapp.movielistapp.data.Repository.NetworkState
import com.raitechmovielistapp.movielistapp.data.Vo.MovieTopRated
import com.raitechmovielistapp.movielistapp.data.Vo.MovieTopRatedResponse
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository:MoviePageListRepository):ViewModel() {

    private val compositeDisposable=CompositeDisposable()

    val moviepagedList:LiveData<PagedList<MovieTopRated>>by lazy{

        movieRepository.fetchLiveMoviePagedList(compositeDisposable)

    }

    val networkState:LiveData<NetworkState> by lazy {

        movieRepository.getNetworkState()
    }

    fun listIsEmpty():Boolean{

        return moviepagedList.value?.isEmpty()?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}