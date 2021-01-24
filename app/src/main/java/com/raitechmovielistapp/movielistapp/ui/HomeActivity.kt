package com.raitechmovielistapp.movielistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.raitechmovielistapp.movielistapp.R
import com.raitechmovielistapp.movielistapp.databinding.ActivityHomeBinding
import com.raitechmovielistapp.movielistapp.ui.TopRatedMovie.TopRatedMovieFragment
import com.raitechmovielistapp.movielistapp.ui.UpcomingMovie.UpComingMovieFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var activityHomeBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding=DataBindingUtil. setContentView(this,R.layout.activity_home)

val adapter=MovieViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(UpComingMovieFragment(),"UPcomingMovie")
        adapter.addFragment(TopRatedMovieFragment(),"TopRatedMovie")

        activityHomeBinding.movieViewpager.adapter=adapter
        tabs.setupWithViewPager(movie_viewpager)
    }

    class MovieViewPagerAdapter(manager:FragmentManager) :FragmentPagerAdapter(manager){

        private val fragmentList:MutableList<Fragment> =ArrayList()
        private val titleList:MutableList<String> = ArrayList()


        override fun getCount(): Int {
           return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        fun addFragment(fragment: Fragment,title:String){

            fragmentList.add(fragment)
            titleList.add(title)

        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}