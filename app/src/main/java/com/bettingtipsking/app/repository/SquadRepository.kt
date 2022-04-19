package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.squad.SquadModel

class SquadRepository(private val fixturesService: FixturesService) {

     val mutableSquadData = MutableLiveData<SquadModel>()
     val mutableProgressData = MutableLiveData<Int>()


    suspend fun getSquad(team: Int) {
        try {
            mutableProgressData.postValue(0)
            val result = fixturesService.getSquad(team);
            if (result?.body() != null){
                mutableProgressData.postValue(1)
                mutableSquadData.postValue(result.body())
            }
        }catch (exception:Exception){
            //todo exception
            mutableProgressData.postValue(1)
        }


    }

    private fun showException(exception: String) {

    }
}