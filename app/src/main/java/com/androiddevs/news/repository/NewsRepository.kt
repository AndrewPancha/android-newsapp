package com.androiddevs.news.repository

import com.androiddevs.news.api.RetrofitInstance

class NewsRepository{
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.getSearchNews(searchQuery, pageNumber)

}