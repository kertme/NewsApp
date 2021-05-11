package com.cemre.newsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.cemre.newsapp.api.NewsApi
import com.cemre.newsapp.data.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: ArticleDatabase.Callback
    ) = Room.databaseBuilder(app, ArticleDatabase::class.java, "task_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .allowMainThreadQueries()
        .build()

    @Provides
    fun provideTaskDao(db: ArticleDatabase) = db.articleDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope