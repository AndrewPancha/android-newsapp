package com.androiddevs.news.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.news.model.Article
import com.androiddevs.news.repository.NewsRepository
import com.androiddevs.news.util.Resource
import kotlinx.coroutines.launch

class BreakingNewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    init {
        getBreakingNews("ua")
    }

    var isLastPage = false

    val progressBarVisibility = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val articles = MutableLiveData<List<Article>>()
    val padding = MutableLiveData<Boolean>()

    private val breakingNewsPage = 1

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        when (response) {
            is Resource.Success -> {
                Log.d(BreakingNewsViewModel::class.java.name, "hiding progress bar")
                progressBarVisibility.value = false
                response.data?.let {
                    Log.d(BreakingNewsViewModel::class.java.name, "showing articles")
                    articles.value = it.articles.toList()
                    val totalPages = it.totalResults / 20 + 2
                    isLastPage = breakingNewsPage == totalPages
                    if (isLastPage) {
                        padding.value = true
                    }
                }
            }
            is Resource.Error -> {
                Log.d(BreakingNewsViewModel::class.java.name, "hiding progress bar")
                progressBarVisibility.value = false
                Log.d(BreakingNewsViewModel::class.java.name, "showing error message")
                errorMessage.value = response.message
            }
            is Resource.Loading -> {
                Log.d(BreakingNewsViewModel::class.java.name, "showing progress bar")
                progressBarVisibility.value = true
            }
        }
    }
}
