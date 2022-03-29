package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel

class FixturesRepository(private val fixturesService: FixturesService) {

    private val mutableLiveFixturesData = MutableLiveData<FixturesModel>()
    private val mutablePastFixturesData = MutableLiveData<FixturesModel>()
    private val mutableTodayFixturesData = MutableLiveData<FixturesModel>()
    private val mutableComingFixturesData = MutableLiveData<FixturesModel>()
    private val mutableHeadToHeadFixturesData = MutableLiveData<FixturesModel>()

    val liveMatchLiveData: LiveData<FixturesModel>
        get() = mutableLiveFixturesData

    val pastMatchLiveData: LiveData<FixturesModel>
        get() = mutablePastFixturesData

    val todayMatchLiveData: LiveData<FixturesModel>
        get() = mutableTodayFixturesData

    val comingMatchLiveData: LiveData<FixturesModel>
        get() = mutableComingFixturesData

    val headToHeadLiveData: LiveData<FixturesModel>
        get() = mutableHeadToHeadFixturesData

    suspend fun getLiveFixtures(live: String) {
        val result = fixturesService.getLiveMatches(live);
        if (result?.body() != null)
            mutableLiveFixturesData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    suspend fun getTodayFixtures(date: String) {
        val result = fixturesService.getFixturesByDate(date);
        if (result?.body() != null)
            mutableTodayFixturesData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    suspend fun getComingFixtures(date: String) {
        val result = fixturesService.getFixturesByDate(date);
        if (result?.body() != null)
            mutableComingFixturesData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    suspend fun getPastFixtures(date: String) {
        val result = fixturesService.getFixturesByDate(date);
        if (result?.body() != null)
            mutableComingFixturesData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    suspend fun getHeadToHeadBetweenTwoTeams(h2h: String) {
        val result = fixturesService.getHeadToHeadBetweenTwoTeams(h2h)
        if (result?.body() != null)
            mutableHeadToHeadFixturesData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    suspend fun getHeadToHeadBetweenTwoTeamsByDate(h2h: String, date: String) {
        val result = fixturesService.getHeadToHeadBetweenTwoTeamsByDate(h2h, date)
        if (result?.body() != null)
            mutableHeadToHeadFixturesData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    private fun showException(exception: String) {

    }
}