package com.cemre.newsapp.api

import com.cemre.newsapp.data.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)