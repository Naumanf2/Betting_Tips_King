package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.model.coach.CoachesModel
import com.bettingtipsking.app.model.fixturesbydate.FixturesByDateModel
import com.bettingtipsking.app.repository.CoachesRepository
import com.bettingtipsking.app.repository.FixturesByDateRepository
import com.bettingtipsking.app.viewmodel.viewmodelfactory.FixturesByDateViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FixturesByDateViewModel(private val fixturesByDateRepository: FixturesByDateRepository) : ViewModel() {

    val date: LiveData<FixturesByDateModel>
        get() = fixturesByDateRepository.date

    init {
    }
    public fun getDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fixturesByDateRepository.getDate(date)
        }
    }
}