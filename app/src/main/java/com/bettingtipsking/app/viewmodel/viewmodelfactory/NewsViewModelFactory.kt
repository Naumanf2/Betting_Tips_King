package com.bettingtipsking.app.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.repository.NewsRepository
import com.bettingtipsking.app.viewmodel.NewsViewModel

class NewsViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}