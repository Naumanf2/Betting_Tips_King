package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.repository.FixturesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LiveFixturesViewModel(private val fixturesService: FixturesService) : ViewModel() {

    private val fixturesRepository = FixturesRepository(fixturesService)

    val liveMatchLiveData: LiveData<FixturesModel>
        get() = fixturesRepository.liveMatchLiveData

    init {
    }

    fun getLiveFixtures(live: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fixturesRepository.getLiveFixtures(live)
        }
    }
}