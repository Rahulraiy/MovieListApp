package com.raitechmovielistapp.movielistapp.data.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.raitechmovielistapp.movielistapp.data.Api.FIRST_PAGE
import com.raitechmovielistapp.movielistapp.data.Api.MovieDBInteface
import com.raitechmovielistapp.movielistapp.data.Vo.MovieTopRated
import com.raitechmovielistapp.movielistapp.data.Vo.MovieUpComing
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieUpComingNetworkDataSource (private val apiService: MovieDBInteface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, MovieUpComing>() {

    private var page= FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieUpComing>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(apiService.getUpcomingMovie(page).subscribeOn(Schedulers.io())
            .subscribe(
                {

                    callback.onResult(it.movieUpComingList, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                }, {

                    networkState.postValue(NetworkState.ERROR)

                }
            ))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieUpComing>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieUpComing>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(apiService.getUpcomingMovie(params.key).subscribeOn(Schedulers.io())
            .subscribe(
                {

                    if (it.totalPages>=params.key){

                        callback.onResult(it.movieUpComingList,params.key+1)
                        networkState.postValue(NetworkState.LOADED)

                    }else{

                        networkState.postValue(NetworkState.ERROR_LIst)
                    }

                }, {

                    networkState.postValue(NetworkState.ERROR)

                }
            ))
    }
}