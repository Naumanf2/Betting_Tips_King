package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.Helper.QuickHelp
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixture_by_fixture_id.FixtureByFixtureId
import com.bettingtipsking.app.model.fixtures.FixturesModel
import java.lang.Exception

class FixturesRepository(private val fixturesService: FixturesService) {

    private val mutableLiveFixturesData = MutableLiveData<FixturesModel>()
    val mutablePastFixturesData = MutableLiveData<FixturesModel>()
    private val mutableTodayFixturesData = MutableLiveData<FixturesModel>()
    private val mutableComingFixturesData = MutableLiveData<FixturesModel>()
    private val mutableHeadToHeadFixturesData = MutableLiveData<FixturesModel>()
    private val mutableMatchDetailsFixturesData = MutableLiveData<FixtureByFixtureId>()

    val mutableProgressTodayFixturesData = MutableLiveData<Int>()
    val mutableProgressLiveFixturesData = MutableLiveData<Int>()
    val mutableProgressComingFixturesData = MutableLiveData<Int>()
    val mutableProgressPastFixturesData = MutableLiveData<Int>()
    val mutableProgressHeadToHeadFixturesData = MutableLiveData<Int>()


    val liveMatchLiveData: LiveData<FixturesModel>
        get() = mutableLiveFixturesData

    val pastMatchLiveData: MutableLiveData<FixturesModel>
        get() = mutablePastFixturesData

    val todayMatchLiveData: LiveData<FixturesModel>
        get() = mutableTodayFixturesData


    val comingMatchLiveData: LiveData<FixturesModel>
        get() = mutableComingFixturesData

    val headToHeadLiveData: LiveData<FixturesModel>
        get() = mutableHeadToHeadFixturesData


    val matchDetailsLiveData: LiveData<FixtureByFixtureId>
        get() = mutableMatchDetailsFixturesData

    suspend fun getLiveFixtures(live: String) {
        try {
            mutableProgressLiveFixturesData.postValue(0)
            val result = fixturesService.getLiveMatches(live);
            if (result?.body() != null) {
                mutableProgressLiveFixturesData.postValue(1)
                mutableLiveFixturesData.postValue(result.body())
            }
        } catch (exception: Exception) {
            mutableProgressLiveFixturesData.postValue(1)
        }//todo exception

    }

    suspend fun getTodayFixtures(date: String) {

        try {
            mutableProgressTodayFixturesData.postValue(0)
            val result = fixturesService.getFixturesByDate(date);
            if (result?.body() != null) {
                mutableProgressTodayFixturesData.postValue(1)
                mutableTodayFixturesData.postValue(result.body())
            }
        } catch (exception: Exception) {
            mutableProgressTodayFixturesData.postValue(1)
        }

    }

    suspend fun getComingFixtures(date: String) {
        mutableProgressComingFixturesData.postValue(0)
        try {
            val result = fixturesService.getFixturesByDate(date);
            if (result?.body() != null) {
                mutableProgressComingFixturesData.postValue(1)
                mutableComingFixturesData.postValue(result.body())
            }
        } catch (exception: Exception) {
            mutableProgressComingFixturesData.postValue(1)
        }

    }

    suspend fun getPastFixtures(date: String) {
        try {
            mutableProgressPastFixturesData.postValue(1)
            val result = fixturesService.getFixturesByDate(date);
            if (result?.body() != null) {
                mutableProgressPastFixturesData.postValue(1)
                mutablePastFixturesData.postValue(result.body())
            }
        } catch (exception: Exception) {
            mutableProgressPastFixturesData.postValue(1)
        }
    }

    suspend fun getFixturesBYFixtureId(id: Int) {
        try {
            val result = fixturesService.getFixturesByFixtureId(id);
            if (result?.body() != null)
                mutableMatchDetailsFixturesData.postValue(result.body())
        } catch (exception: Exception) {
            //todo exception
        }
    }

    suspend fun getHeadToHeadBetweenTwoTeams(h2h: String) {
        try {
            mutableProgressHeadToHeadFixturesData.postValue(0)
            val result = fixturesService.getHeadToHeadBetweenTwoTeams(h2h)
            if (result?.body() != null) {
                mutableProgressHeadToHeadFixturesData.postValue(1)
                mutableHeadToHeadFixturesData.postValue(result.body())
            }
        } catch (exception: Exception) {
            mutableProgressHeadToHeadFixturesData.postValue(1)

        }


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