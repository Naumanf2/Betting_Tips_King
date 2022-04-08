package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.repository.FixturesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PastFixturesViewModel(private val fixturesService: FixturesService) : ViewModel()  {

    private val fixturesRepository = FixturesRepository(fixturesService)

    val pastMatchLiveData: MutableLiveData<FixturesModel>
        get() = fixturesRepository.mutablePastFixturesData

    init {
    }

    fun getPastFixtures(data: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fixturesRepository.getPastFixtures(data)
        }
    }
}