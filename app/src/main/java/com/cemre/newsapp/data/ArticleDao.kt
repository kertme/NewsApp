package com.cemre.newsapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {

    // allowing to insert unique objects
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArticle(article: FavoriteArticle)

    @Query("SELECT * FROM favorite_articles_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<FavoriteArticle>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_articles_table WHERE url = :url)")
    fun checkDataExist(url : String) : Boolean

    @Update
    suspend fun update(article: FavoriteArticle)

    @Query("DELETE FROM favorite_articles_table WHERE url = :url")
    fun detele(url: String)

}