package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.predictions.PredictionsModel
import com.bettingtipsking.app.repository.FixturesRepository
import com.bettingtipsking.app.repository.PredictionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PredictionsViewModel(private val fixturesService: FixturesService) : ViewModel() {

    private val predicationRepository = PredictionsRepository(fixturesService);

    val predictionsLiveData: LiveData<PredictionsModel>
        get() = predicationRepository.predictionsLiveData

    init {
    }
     fun getPredictions(feature: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            predicationRepository.getPredictions(feature)

        }
    }
}