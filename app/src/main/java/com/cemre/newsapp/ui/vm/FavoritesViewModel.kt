package com.cemre.newsapp.ui.vm

import androidx.lifecycle.*
import com.cemre.newsapp.data.ArticleDao
import com.cemre.newsapp.data.FavoriteArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor (private val articleDao: ArticleDao) : ViewModel(){

    val articles = articleDao.readAllData()
    val checkResult: MutableLiveData<Boolean> = MutableLiveData()


    fun addArticle(article: FavoriteArticle){

        viewModelScope.launch {
            articleDao.addArticle(article)
        }
    }

    fun checkArticle(articleUrl: String) {

        viewModelScope.launch{
            checkResult.postValue(articleDao.checkDataExist(articleUrl))
        }
    }

    fun deleteArticle(articleUrl: String){
        viewModelScope.launch {
            articleDao.detele(articleUrl)
        }
    }


}