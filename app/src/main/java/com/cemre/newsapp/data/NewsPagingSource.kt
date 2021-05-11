package com.cemre.newsapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cemre.newsapp.api.NewsApi
import retrofit2.HttpException
import java.io.IOException

private const val NEWS_STARTING_PAGE_INDEX = 1

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val query: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX

        return try {

            val response = newsApi.searchNews(query, position)
            val news = response.articles
            LoadResult.Page(
                data = news,
                prevKey = if (position == NEWS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (news.isEmpty()) null else position + 1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    }
}