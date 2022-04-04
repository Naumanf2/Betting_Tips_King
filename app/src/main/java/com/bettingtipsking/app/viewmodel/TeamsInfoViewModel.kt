package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.league.LeagueModel
import com.bettingtipsking.app.model.teamsInfo.TeamsInfoModel
import com.bettingtipsking.app.repository.LeagueRepository
import com.bettingtipsking.app.repository.TeamsInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamsInfoViewModel (private val fixturesService: FixturesService) : ViewModel() {

    private val teamsInfoRepository= TeamsInfoRepository(fixturesService)

    val data: LiveData<TeamsInfoModel>
        get() = teamsInfoRepository.data

    init {
    }

    public fun getLeague(id: Int , season : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            teamsInfoRepository.getTeamsInfo(id, season)
        }
    }
}