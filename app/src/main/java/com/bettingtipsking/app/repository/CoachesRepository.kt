package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.coach.CoachesModel

class CoachesRepository(private val matchesService: FixturesService) {


    private val LiveData = MutableLiveData<CoachesModel>()

    val coach: LiveData<CoachesModel>
        get() = LiveData

    suspend fun getCoach(team: Int) {
        val result = matchesService.getCoach(team);
        if (result?.body() != null) {
            LiveData.postValue(result.body())
            println("Result is"+result.body())
        }
    }
}