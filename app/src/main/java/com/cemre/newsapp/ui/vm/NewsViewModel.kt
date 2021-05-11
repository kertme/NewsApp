package com.cemre.newsapp.ui.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cemre.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (
    private val repository: NewsRepository,
    private val state: SavedStateHandle
        ) : ViewModel() {


    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
    val news = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchNews(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "besiktas"
    }
}
