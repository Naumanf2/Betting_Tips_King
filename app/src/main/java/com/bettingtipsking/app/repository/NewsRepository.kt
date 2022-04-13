package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.NewsService
import com.bettingtipsking.app.model.news.NewsModel

class NewsRepository(private val newsService: NewsService) {

    private val newsLiveData = MutableLiveData<NewsModel>()

    val news: LiveData<NewsModel>
        get() = newsLiveData

    suspend fun getNews(apiKey: String, page: Int) {
        val result = newsService.getNews(apiKey, page);
        if (result?.body() != null) {
            newsLiveData.postValue(result.body())
        } else {
            newsLiveData.postValue(null)
        }
    }
}