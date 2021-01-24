package com.raitechmovielistapp.movielistapp.data.Api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY="3038b37245c36bb4eecdb18cdfa9bb79"
const val BASE_URl="https://api.themoviedb.org/3/"
const val POSTER_BASE_URL="https://image.tmdb.org/t/p/w342"
const val FIRST_PAGE=1
const val POST_PER_PAGE=20

object TheMovieDBClient {

fun getClient():MovieDBInteface{

    val requestintercepter=Interceptor{chain->

        val url=chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val request=chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    val okHttpClient= OkHttpClient.Builder()
        .addInterceptor(requestintercepter)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
    return Retrofit.Builder().client(okHttpClient)
        .baseUrl(BASE_URl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieDBInteface::class.java)

}
}