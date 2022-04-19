package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixture_by_fixture_id.FixtureByFixtureId
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.model.league.LeagueModel
import com.bettingtipsking.app.model.leaguebyleague.LeagueByLeague
import com.bettingtipsking.app.repository.FixturesRepository
import com.bettingtipsking.app.repository.LeagueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchDetailsViewModel(private val fixturesService: FixturesService) : ViewModel() {

    private val fixturesRepository = FixturesRepository(fixturesService)
    private val leagueRepository = LeagueRepository(fixturesService)


    val matchDetailsLiveData: LiveData<FixtureByFixtureId>
        get() = fixturesRepository.matchDetailsLiveData

    val leagueByIdLiveData: LiveData<LeagueByLeague>
        get() = leagueRepository.leagueByIdLiveData


    fun getFixturesBYFixtureId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fixturesRepository.getFixturesBYFixtureId(id)
        }
    }

    fun getLeagueById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            leagueRepository.getLeagueById(id)
        }
    }
}