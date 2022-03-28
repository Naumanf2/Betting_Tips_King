package com.bettingtipsking.app.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.repository.NewsRepository
import com.bettingtipsking.app.repository.PredictionsRepository
import com.bettingtipsking.app.viewmodel.NewsViewModel
import com.bettingtipsking.app.viewmodel.PredictionsViewModel

class PredictionsViewModelFactory(private val fixturesService: FixturesService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PredictionsViewModel(fixturesService) as T
    }
}