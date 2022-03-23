package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.api.MatchesService

import com.bettingtipsking.app.model.predictions.PredictionsModel

class PredictionsRepository(private val matchesService: MatchesService) {

    private val LiveData = MutableLiveData<PredictionsModel>()

    val matches: LiveData<PredictionsModel>
        get() = LiveData

    suspend fun getPredictions(feature: Int) {
        val result = matchesService.getPredictions(feature);
        if (result?.body() != null) {
            LiveData.postValue(result.body())
        }
    }
}