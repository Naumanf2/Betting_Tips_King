package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.league.LeagueModel
import com.bettingtipsking.app.model.teamsInfo.TeamsInfoModel

class TeamsInfoRepository (private val fixturesService: FixturesService) {


    private val liveData = MutableLiveData<TeamsInfoModel>()

    val data: LiveData<TeamsInfoModel>
        get() = liveData

    suspend fun getTeamsInfo(id: Int , season: Int) {
        val result = fixturesService.getTeamsInfo(id,season);
        if (result?.body() != null) {
            liveData.postValue(result.body())

        }
    }
}