package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.coach.CoachesModel
import com.bettingtipsking.app.model.predictions.PredictionsModel
import com.bettingtipsking.app.repository.CoachesRepository
import com.bettingtipsking.app.repository.FixturesByDateRepository
import com.bettingtipsking.app.repository.PredictionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoachesViewModel(private val fixturesService: FixturesService) : ViewModel() {

    private val coachesRepository = CoachesRepository(fixturesService);

    val coachesLiveDataA: LiveData<CoachesModel>
        get() = coachesRepository.coachesLiveDataA

    val coachesLiveDataB: LiveData<CoachesModel>
        get() = coachesRepository.coachesLiveDataB


    public fun getCoachA(team: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            coachesRepository.getCoachA(team)
        }
    }

    public fun getCoachB(team: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            coachesRepository.getCoachB(team)
        }
    }
}