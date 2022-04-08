package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.coach.CoachesModel

class CoachesRepository(private val matchesService: FixturesService) {


    private val coachesMutableLiveData = MutableLiveData<CoachesModel>()

    val coachesLiveData: LiveData<CoachesModel>
        get() = coachesMutableLiveData

    suspend fun getCoach(team: Int) {
        val result = matchesService.getCoach(team);
        if (result?.body() != null)
            coachesMutableLiveData.postValue(result.body())
        else
            showException(result.errorBody().toString())

    }

    private fun showException(exception: String) {

    }
}