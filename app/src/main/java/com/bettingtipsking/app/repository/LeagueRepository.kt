package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.model.league.LeagueModel

class LeagueRepository(private val fixturesService: FixturesService) {


    private val liveData = MutableLiveData<LeagueModel>()

    val data: LiveData<LeagueModel>
        get() = liveData

    suspend fun getLeague() {
        val result = fixturesService.getLeague();
        if (result?.body() != null) {
            liveData.postValue(result.body())

            println("League is" + result.body())
        }
    }
}