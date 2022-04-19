package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.model.statistics.FixturesStatistics
import com.bettingtipsking.app.repository.FixturesRepository
import com.bettingtipsking.app.repository.FixturesStatisticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FixturesStatisticsViewModel(val fixturesService: FixturesService) : ViewModel() {

    private val fixturesStatisticsRepository = FixturesStatisticsRepository(fixturesService)

    val liveMatchLiveData: LiveData<FixturesStatistics>
        get() = fixturesStatisticsRepository.liveData


    fun getLiveFixtures(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fixturesStatisticsRepository.getFixturesStatistics(id)
        }
    }
}