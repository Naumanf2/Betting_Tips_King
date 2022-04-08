package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.model.league.LeagueModel

class LeagueRepository(private val fixturesService: FixturesService) {


    private val liveData = MutableLiveData<LeagueModel>()
    private val leagueByIdMutableLiveData = MutableLiveData<LeagueModel>()

    val data: LiveData<LeagueModel>
        get() = liveData

    val leagueByIdLiveData: LiveData<LeagueModel>
        get() = leagueByIdMutableLiveData

    suspend fun getLeague() {
        val result = fixturesService.getLeague();
        if (result?.body() != null) {
            liveData.postValue(result.body())

            println("League is" + result.body())
        }
    }

    suspend fun getLeagueById(id:Int) {
        val result = fixturesService.getLeagueById(id);
        if (result?.body() != null) {
            leagueByIdMutableLiveData.postValue(result.body())
        }
    }
}