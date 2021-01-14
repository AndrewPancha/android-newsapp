package com.androiddevs.news.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.news.model.Article
import com.androiddevs.news.repository.NewsRepository
import com.androiddevs.news.util.Resource
import kotlinx.coroutines.launch

class SearchNewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    var isLastPage = false
    val progressBarVisibility = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val articles = MutableLiveData<List<Article>>()

    var searchNewsPage = 1

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        when (response) {
            is Resource.Success -> {
                Log.d(SearchNewsViewModel::class.java.name, "hiding progress bar")
                progressBarVisibility.value = false
                response.data?.let {
                    Log.d(SearchNewsViewModel::class.java.name, "showing articles")
                    articles.value = it.articles.toList()
                    val totalPages = it.totalResults / 20 + 2
                    isLastPage = searchNewsPage == totalPages
                }
            }
            is Resource.Error -> {
                Log.d(SearchNewsViewModel::class.java.name, "hiding progress bar")
                progressBarVisibility.value = false
                Log.d(SearchNewsViewModel::class.java.name, "showing error message")
                errorMessage.value = response.message
            }
            is Resource.Loading -> {
                Log.d(SearchNewsViewModel::class.java.name, "showing progress bar")
                progressBarVisibility.value = true
            }
        }
    }
}
