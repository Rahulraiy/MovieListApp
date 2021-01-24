package com.raitechmovielistapp.movielistapp.ui.UpcomingMovie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raitechmovielistapp.movielistapp.R
import com.raitechmovielistapp.movielistapp.data.Api.POSTER_BASE_URL
import com.raitechmovielistapp.movielistapp.data.Repository.NetworkState
import com.raitechmovielistapp.movielistapp.data.Vo.MovieUpComing
import com.raitechmovielistapp.movielistapp.databinding.NetworkStateitemBinding
import com.raitechmovielistapp.movielistapp.databinding.TopratedmovieListitemBinding
import kotlinx.android.synthetic.main.network_stateitem.view.*
import kotlinx.android.synthetic.main.topratedmovie_listitem.view.*


class UpComingMovieListAdapter(public val context: Context) :
    PagedListAdapter<MovieUpComing, RecyclerView.ViewHolder>(UpComingMovieListAdapter.MovieDiffCallBack()) {
    val MOVIE_VIEW_TYPE=1
    val NETWORK_VIEW_TYPE=2
    private var networkState: NetworkState?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val movieListitemBinding:TopratedmovieListitemBinding
        if (viewType==MOVIE_VIEW_TYPE){
            movieListitemBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.topratedmovie_listitem,parent,false)

            return MovieItemViewHolder(movieListitemBinding)

        }else{
            val stateitemBinding:NetworkStateitemBinding
            stateitemBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.network_stateitem,parent,false)

            return NetworkStateItemViewHolder(stateitemBinding)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position)==MOVIE_VIEW_TYPE){

            (holder as UpComingMovieListAdapter.MovieItemViewHolder).bind(getItem(position),context)

        }
        else{

            (holder as UpComingMovieListAdapter.NetworkStateItemViewHolder).bind(networkState)
        }
    }
    private fun hasExtraRaw():Boolean{

        return networkState!=null && networkState!= NetworkState.LOADED

    }

    override fun getItemCount(): Int {
        return super.getItemCount()+if (hasExtraRaw()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRaw() && position==itemCount-1){
            NETWORK_VIEW_TYPE
        }else{
            MOVIE_VIEW_TYPE
        }
    }

    class MovieDiffCallBack: DiffUtil.ItemCallback<MovieUpComing>(){
        override fun areItemsTheSame(oldItem: MovieUpComing, newItem: MovieUpComing): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieUpComing, newItem: MovieUpComing): Boolean {
            return oldItem==newItem
        }

    }

    class MovieItemViewHolder(movieListitemBinding: TopratedmovieListitemBinding):RecyclerView.ViewHolder(movieListitemBinding.root){

        fun bind(movieUpComing: MovieUpComing?,context: Context){

            itemView.tv_movietitle.text=movieUpComing?.title
            val movieposturl= POSTER_BASE_URL +movieUpComing?.posterPath
            Glide.with(itemView.context)
                .load(movieposturl)
                .into(itemView.img_topratedmovie)

        }

    }

    class NetworkStateItemViewHolder(stateitemBinding: NetworkStateitemBinding):RecyclerView.ViewHolder(stateitemBinding.root){

        fun bind(networkState: NetworkState?){

            if (networkState!=null&& networkState==NetworkState.LOADING){

                itemView.item_progressbar.visibility=View.VISIBLE
            }
            else{
                itemView.item_progressbar.visibility=View.GONE
            }
            if (networkState!=null&&networkState==NetworkState.ERROR){

                itemView.tv_errormsg.visibility=View.VISIBLE
                itemView.tv_errormsg.text=networkState.msg

            }

            else if(networkState!=null&&networkState==NetworkState.ERROR_LIst) {
                itemView.tv_errormsg.visibility = View.VISIBLE
                itemView.tv_errormsg.text = networkState.msg
            }
            else{
                itemView.tv_errormsg.visibility=View.GONE
            }
        }

    }

    fun setNetworkState(networkState: NetworkState){

        val previousstate=this.networkState
        val hadExtraRow=hasExtraRaw()
        this.networkState=networkState
        val hasExtraRow=hasExtraRaw()

        if (hadExtraRow!=hasExtraRow){
            if (hadExtraRow){

                notifyItemRemoved(super.getItemCount())

            }else{
                notifyItemInserted(super.getItemCount())

            }

        }else if (hasExtraRow && previousstate!=networkState){

            notifyItemChanged(itemCount-1)
        }
    }

}