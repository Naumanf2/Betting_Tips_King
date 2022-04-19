package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.statistics.FixturesStatistics

class FixturesStatisticsRepository(private val fixturesService: FixturesService) {

    private val mutableLiveData = MutableLiveData<FixturesStatistics>()

    val liveData: LiveData<FixturesStatistics>
        get() = mutableLiveData


    suspend fun getFixturesStatistics(id: Int) {
        val result = fixturesService.getFixturesStatistics(id);
        if (result?.body() != null)
            mutableLiveData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    private fun showException(exception: String) {

    }

}