package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.FixturesService

import com.bettingtipsking.app.model.predictions.PredictionsModel

class PredictionsRepository(private val matchesService: FixturesService) {

    private val predictionsMutableLiveData = MutableLiveData<PredictionsModel>()
     val progressMutableLiveData = MutableLiveData<Int>()

    val predictionsLiveData: LiveData<PredictionsModel>
        get() = predictionsMutableLiveData

    suspend fun getPredictions(feature: Int) {
        try {
            progressMutableLiveData.postValue(0)
            val result = matchesService.getPredictions(feature);
            if (result?.body() != null) {
                progressMutableLiveData.postValue(1)
                predictionsMutableLiveData.postValue(result.body())
            }
        } catch (exception: Exception) {
            //todo exception
            progressMutableLiveData.postValue(1)
        }

    }
}