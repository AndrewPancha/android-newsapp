package com.androiddevs.news.repository

import com.androiddevs.news.api.RetrofitInstance
import com.androiddevs.news.model.NewsResponse
import com.androiddevs.news.util.Resource

class NewsRepository {
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Resource<NewsResponse> {
        val response = RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse> {
        val response = RetrofitInstance.api.getSearchNews(searchQuery, pageNumber)
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
