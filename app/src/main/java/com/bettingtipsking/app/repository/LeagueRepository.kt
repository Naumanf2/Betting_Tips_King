package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.league.LeagueModel

class LeagueRepository(private val fixturesService: FixturesService) {

    private val leaguesMutableLiveData = MutableLiveData<LeagueModel>()
    private val leagueByIdMutableLiveData = MutableLiveData<LeagueModel>()

    val leaguesLiveData: LiveData<LeagueModel>
        get() = leaguesMutableLiveData

    val leagueByIdLiveData: LiveData<LeagueModel>
        get() = leagueByIdMutableLiveData

    suspend fun getLeagues() {
        val result = fixturesService.getLeague();
        if (result?.body() != null) {
            leaguesMutableLiveData.postValue(result.body())
        }else{
            //todo something
        }
    }

    suspend fun getLeagueById(id:Int) {
        val result = fixturesService.getLeagueById(id);
        if (result?.body() != null) {
            leagueByIdMutableLiveData.postValue(result.body())
        }else{
            //todo something
        }
    }
}