package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.NewsService
import com.bettingtipsking.app.model.news.NewsModel
import com.bettingtipsking.app.repository.NewsRepository
import com.google.android.gms.common.api.internal.ApiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val news: LiveData<NewsModel>
        get() = newsRepository.news

    public fun news(apiKey: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getNews(apiKey, page )

        }
    }
}