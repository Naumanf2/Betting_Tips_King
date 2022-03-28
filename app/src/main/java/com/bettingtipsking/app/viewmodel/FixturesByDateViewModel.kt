package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.repository.FixturesByDateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FixturesByDateViewModel(private val fixturesByDateRepository: FixturesByDateRepository) : ViewModel() {

    val date: LiveData<FixturesModel>
        get() = fixturesByDateRepository.date

    init {
    }
    public fun getDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fixturesByDateRepository.getDate(date)
        }
    }
}