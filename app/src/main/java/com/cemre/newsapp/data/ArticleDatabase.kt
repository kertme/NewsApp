package com.cemre.newsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cemre.newsapp.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [FavoriteArticle::class], version = 1, exportSchema = false)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    class Callback @Inject constructor(
        private val database: Provider<ArticleDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
        }
    }

}