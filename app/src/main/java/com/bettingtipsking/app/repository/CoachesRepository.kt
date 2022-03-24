package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.MatchesService
import com.bettingtipsking.app.model.coach.CoachesModel
import com.bettingtipsking.app.model.predictions.PredictionsModel

class CoachesRepository(private val matchesService: MatchesService) {


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