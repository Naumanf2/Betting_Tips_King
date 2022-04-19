package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.league.LeagueModel
import com.bettingtipsking.app.model.leaguebyleague.LeagueByLeague

class LeagueRepository(private val fixturesService: FixturesService) {

    private val leaguesMutableLiveData = MutableLiveData<LeagueModel>()
    private val leagueByIdMutableLiveData = MutableLiveData<LeagueByLeague>()

    val leaguesLiveData: LiveData<LeagueModel>
        get() = leaguesMutableLiveData

    val leagueByIdLiveData: LiveData<LeagueByLeague>
        get() = leagueByIdMutableLiveData

    suspend fun getLeagues() {
        val result = fixturesService.getLeague();
        if (result?.body() != null) {
            leaguesMutableLiveData.postValue(result.body())
        } else {
            //todo something
        }
    }

    suspend fun getLeagueById(id: Int) {
        try {
            val result = fixturesService.getLeaguesBYLeagueId(id);
            if (result?.body() != null) {
                System.out.println("result is "+result.body())

                leagueByIdMutableLiveData.postValue(result.body())

            }
        } catch (exception: Exception) {
            System.out.println("result is expec")
            //todo exception
        }

    }
}