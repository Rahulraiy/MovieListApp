package com.raitechmovielistapp.movielistapp.data.Repository



import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.raitechmovielistapp.movielistapp.data.Api.FIRST_PAGE
import com.raitechmovielistapp.movielistapp.data.Api.MovieDBInteface
import com.raitechmovielistapp.movielistapp.data.Vo.MovieTopRated
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io


class MovieNetworkDataSource(private val apiService:MovieDBInteface,private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int,MovieTopRated>() {

    private var page= FIRST_PAGE
    val networkState:MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieTopRated>
    ) {

        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(apiService.getTopratedMovie(page).subscribeOn(Schedulers.io())
            .subscribe(
                {

                    callback.onResult(it.movieList, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                }, {

                    networkState.postValue(NetworkState.ERROR)

                }
            ))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieTopRated>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieTopRated>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(apiService.getTopratedMovie(params.key).subscribeOn(Schedulers.io())
            .subscribe(
                {

                  if (it.totalPages>=params.key){

                      callback.onResult(it.movieList,params.key+1)
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