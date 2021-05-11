package com.cemre.newsapp.api

import com.cemre.newsapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    companion object{
        const val BASE_URL = "https://newsapi.org/"
        const val CLIENT_ID = BuildConfig.NEWS_ACCESS_KEY
    }

    //@Headers("Accept-Version: v2", "Authorization: Client-ID $CLIENT_ID")
    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = CLIENT_ID
    ): NewsResponse
}