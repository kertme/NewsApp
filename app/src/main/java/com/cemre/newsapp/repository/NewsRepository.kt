package com.cemre.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.cemre.newsapp.api.NewsApi
import com.cemre.newsapp.data.NewsPagingSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(private val newsApi: NewsApi){

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(newsApi, query) }
        ).liveData
}