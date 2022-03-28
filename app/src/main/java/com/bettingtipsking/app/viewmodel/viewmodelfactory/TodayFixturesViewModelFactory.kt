package com.bettingtipsking.app.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.viewmodel.LiveFixturesViewModel
import com.bettingtipsking.app.viewmodel.TodayFixturesViewModel

class TodayFixturesViewModelFactory(private val fixturesService: FixturesService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodayFixturesViewModel(fixturesService) as T
    }
}