package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService

import com.bettingtipsking.app.model.predictions.PredictionsModel

class PredictionsRepository(private val matchesService: FixturesService) {

    private val predictionsMutableLiveData = MutableLiveData<PredictionsModel>()

    val predictionsLiveData: LiveData<PredictionsModel>
        get() =  predictionsMutableLiveData

     suspend fun getPredictions(feature: Int) {
        val result = matchesService.getPredictions(feature);
        if (result?.body() != null) {
            predictionsMutableLiveData.postValue(result.body())
        }
    }
}