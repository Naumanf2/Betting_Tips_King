package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.squad.SquadModel

class SquadRepository(private val fixturesService: FixturesService) {

    private val mutableSquadData = MutableLiveData<SquadModel>()

    val squadLiveData: LiveData<SquadModel>
        get() = mutableSquadData

    suspend fun getSquad(team: String) {
        val result = fixturesService.getSquad(team);
        if (result?.body() != null)
            mutableSquadData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    private fun showException(exception: String) {

    }
}