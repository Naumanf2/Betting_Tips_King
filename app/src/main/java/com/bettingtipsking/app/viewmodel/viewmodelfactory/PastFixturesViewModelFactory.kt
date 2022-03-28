package com.bettingtipsking.app.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.viewmodel.PastFixturesViewModel
import com.bettingtipsking.app.viewmodel.TodayFixturesViewModel

class PastFixturesViewModelFactory(private val fixturesService: FixturesService) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PastFixturesViewModel(fixturesService) as T
    }
}