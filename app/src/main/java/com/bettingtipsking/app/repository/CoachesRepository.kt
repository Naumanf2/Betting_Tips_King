package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.coach.CoachesModel

class CoachesRepository(private val matchesService: FixturesService) {


    private val coachesMutableLiveDataA = MutableLiveData<CoachesModel>()
    private val coachesMutableLiveDataB = MutableLiveData<CoachesModel>()

    val coachesLiveDataA: LiveData<CoachesModel>
        get() = coachesMutableLiveDataA

    val coachesLiveDataB: LiveData<CoachesModel>
        get() = coachesMutableLiveDataB

    suspend fun getCoachA(team: Int) {

        try {
            val result = matchesService.getCoach(team);
            if (result?.body() != null)
                coachesMutableLiveDataA.postValue(result.body())
        } catch (exception: Exception) {
            //todo exception
        }

    }

    suspend fun getCoachB(team: Int) {
        try {
            val result = matchesService.getCoach(team);
            if (result?.body() != null)
                coachesMutableLiveDataB.postValue(result.body())
        } catch (exception: Exception) {
            //todo exception
        }


    }


}