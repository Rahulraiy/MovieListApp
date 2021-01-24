package com.raitechmovielistapp.movielistapp.ui.UpcomingMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.raitechmovielistapp.movielistapp.data.Repository.NetworkState
import com.raitechmovielistapp.movielistapp.data.Vo.MovieUpComing
import io.reactivex.disposables.CompositeDisposable

class UpcomingViewModel (private val upcomingmovieRepository: UpComingMovieListRepository): ViewModel()  {

    private val compositeDisposable= CompositeDisposable()

    val moviepagedList: LiveData<PagedList<MovieUpComing>> by lazy{

        upcomingmovieRepository.fetchLiveMovieupcomingPagedList(compositeDisposable)

    }

    val networkState:LiveData<NetworkState> by lazy {

        upcomingmovieRepository.getNetworkState()
    }

    fun listIsEmpty():Boolean{

        return moviepagedList.value?.isEmpty()?:true
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}