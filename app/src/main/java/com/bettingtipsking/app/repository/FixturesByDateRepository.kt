package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.MatchesService
import com.bettingtipsking.app.model.fixturesbydate.FixturesByDateModel


class FixturesByDateRepository(private val matchesService: MatchesService) {


    private val liveData = MutableLiveData<FixturesByDateModel>()

    val date: LiveData<FixturesByDateModel>
        get() = liveData

    suspend fun getDate(date: String) {
        val result = matchesService.getFixtureByDate(date);
        if (result?.body() != null) {
            liveData.postValue(result.body())
            println("Date is"+result.body())
        }
    }
}




