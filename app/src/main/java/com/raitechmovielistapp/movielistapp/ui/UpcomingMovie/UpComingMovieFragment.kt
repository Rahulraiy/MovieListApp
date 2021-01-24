package com.raitechmovielistapp.movielistapp.ui.UpcomingMovie

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
import com.raitechmovielistapp.movielistapp.databinding.FragmentUpComingMovieBinding


class UpComingMovieFragment : Fragment() {
   lateinit var  upComingMovieBinding:FragmentUpComingMovieBinding
    private lateinit var upcomingmovieviewmodel: UpcomingViewModel
    lateinit var upComingMovieListRepository: UpComingMovieListRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        upComingMovieBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_up_coming_movie,container,false)


        val apiservice: MovieDBInteface = TheMovieDBClient.getClient()
        upComingMovieListRepository= UpComingMovieListRepository(apiservice)
        upcomingmovieviewmodel=getViewmodel()

        val movieAdapter= UpComingMovieListAdapter(requireContext())
        val gridLayoutmanager= GridLayoutManager(requireContext(),3)

        gridLayoutmanager.spanSizeLookup=object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewtype: Int = movieAdapter.getItemViewType(position)
                if (viewtype == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3

            }
        }

        upComingMovieBinding.rvUpcomingMovie.layoutManager=gridLayoutmanager
        upComingMovieBinding.rvUpcomingMovie.setHasFixedSize(true)
        upComingMovieBinding.rvUpcomingMovie.adapter=movieAdapter


        upcomingmovieviewmodel.moviepagedList.observe(requireActivity(), Observer {
            movieAdapter.submitList(it)
        })

        upcomingmovieviewmodel.networkState.observe(requireActivity(), Observer {

            upComingMovieBinding.upcomingProgressbar.visibility= if (upcomingmovieviewmodel.listIsEmpty()&& it== NetworkState.LOADING) View.VISIBLE else View.GONE
            upComingMovieBinding.tvUpcomingError.visibility= if (upcomingmovieviewmodel.listIsEmpty()&& it== NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!upcomingmovieviewmodel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }

        })


        return upComingMovieBinding.root
    }

    private fun getViewmodel(): UpcomingViewModel {

        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{

            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED CAST")
                return UpcomingViewModel(upComingMovieListRepository) as T
            }
        })[UpcomingViewModel::class.java]

    }


}