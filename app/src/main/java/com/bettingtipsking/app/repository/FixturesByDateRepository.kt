package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel


class FixturesByDateRepository(private val matchesService: FixturesService) {


    private val liveData = MutableLiveData<FixturesModel>()

    val date: LiveData<FixturesModel>
        get() = liveData

    suspend fun getDate(date: String) {
        val result = matchesService.getFixturesByDate(date);
        if (result?.body() != null) {
            liveData.postValue(result.body())
            println("Date is"+result.body())
        }
    }
}




