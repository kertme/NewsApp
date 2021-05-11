package com.cemre.newsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_articles_table")
data class FavoriteArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?

)
