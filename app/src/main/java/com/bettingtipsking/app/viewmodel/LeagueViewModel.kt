package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.model.league.LeagueModel
import com.bettingtipsking.app.repository.FixturesByDateRepository
import com.bettingtipsking.app.repository.LeagueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeagueViewModel(private val fixturesService: FixturesService) : ViewModel() {

    private val leagueRepository = LeagueRepository(fixturesService)

    val data: LiveData<LeagueModel>
        get() = leagueRepository.data


    val leagueByIdLiveData: LiveData<LeagueModel>
        get() = leagueRepository.leagueByIdLiveData

    public fun getLeague() {
        viewModelScope.launch(Dispatchers.IO) {
            leagueRepository.getLeague()
        }
    }

    public fun getLeagueById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            leagueRepository.getLeagueById(id)
        }
    }
}