package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.MatchesService
import com.bettingtipsking.app.model.news.NewsModel
import com.bettingtipsking.app.model.predictions.PredictionsModel
import com.bettingtipsking.app.repository.NewsRepository
import com.bettingtipsking.app.repository.PredictionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PredictionsViewModel(private val predicationRepository: PredictionsRepository) : ViewModel() {

    val matches: LiveData<PredictionsModel>
        get() = predicationRepository.matches

    init {
    }
    public fun getPredictions(feature: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            predicationRepository.getPredictions(feature)

        }
    }
}