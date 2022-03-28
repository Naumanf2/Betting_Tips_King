package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.repository.FixturesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class H2HViewModel(private val fixturesService: FixturesService) :
    ViewModel() {
    private val fixturesRepository = FixturesRepository(fixturesService)

    val headToHeadLiveData: LiveData<FixturesModel>
        get() = fixturesRepository.headToHeadLiveData

    init {
    }

    fun getHeadToHeadBetweenTwoTeams(h2h: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fixturesRepository.getHeadToHeadBetweenTwoTeams(h2h)
        }
    }

    fun getHeadToHeadBetweenTwoTeams(h2h: String, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fixturesRepository.getHeadToHeadBetweenTwoTeamsByDate(h2h, date)
        }
    }
}