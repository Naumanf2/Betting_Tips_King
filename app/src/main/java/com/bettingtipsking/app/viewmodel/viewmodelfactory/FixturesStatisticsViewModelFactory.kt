package com.bettingtipsking.app.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.viewmodel.FixturesStatisticsViewModel

class FixturesStatisticsViewModelFactory(private val fixturesService: FixturesService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FixturesStatisticsViewModel(fixturesService) as T
    }
}