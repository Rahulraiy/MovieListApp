package com.raitechmovielistapp.movielistapp.ui.TopRatedMovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.raitechmovielistapp.movielistapp.R
import com.raitechmovielistapp.movielistapp.data.Api.MovieDBInteface
import com.raitechmovielistapp.movielistapp.data.Api.TheMovieDBClient
import com.raitechmovielistapp.movielistapp.data.Repository.NetworkState
import com.raitechmovielistapp.movielistapp.databinding.FragmentTopRatedMovieBinding


class TopRatedMovieFragment : Fragment() {
    lateinit var ratedmoviefragmentBinding:FragmentTopRatedMovieBinding
    private lateinit var viewmodel: MainActivityViewModel
    lateinit var moviePageListRepository: MoviePageListRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        ratedmoviefragmentBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_top_rated_movie, container, false)


        val apiservice: MovieDBInteface = TheMovieDBClient.getClient()
        moviePageListRepository= MoviePageListRepository(apiservice)
        viewmodel=getViewmodel()

        val movieAdapter= TopRatedMovieListAdapter(requireContext())
        val gridLayoutmanager= GridLayoutManager(requireContext(),3)

        gridLayoutmanager.spanSizeLookup=object:GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewtype:Int=movieAdapter.getItemViewType(position)
                if (viewtype==movieAdapter.MOVIE_VIEW_TYPE)return 1
                else return 3

            }
        }

        ratedmoviefragmentBinding.rvTopratedMovie.layoutManager=gridLayoutmanager
        ratedmoviefragmentBinding.rvTopratedMovie.setHasFixedSize(true)
        ratedmoviefragmentBinding.rvTopratedMovie.adapter=movieAdapter

        viewmodel.moviepagedList.observe(requireActivity(), Observer {
            movieAdapter.submitList(it)
        })


        viewmodel.networkState.observe(requireActivity(), Observer {

            ratedmoviefragmentBinding.mainProgressbar.visibility= if (viewmodel.listIsEmpty()&& it== NetworkState.LOADING)View.VISIBLE else View.GONE
            ratedmoviefragmentBinding.tvTopratedmovieError.visibility= if (viewmodel.listIsEmpty()&& it== NetworkState.ERROR)View.VISIBLE else View.GONE

            if (!viewmodel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }

        })

        return ratedmoviefragmentBinding.root
    }
    private fun getViewmodel():MainActivityViewModel{

        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{

            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED CAST")
                return MainActivityViewModel(moviePageListRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }

}