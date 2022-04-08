package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.model.squad.SquadModel
import com.bettingtipsking.app.repository.FixturesRepository
import com.bettingtipsking.app.repository.SquadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SquadViewModel(private val fixturesService: FixturesService) : ViewModel()  {

    private val squadRepository = SquadRepository(fixturesService)

    val squadLiveData: LiveData<SquadModel>
        get() = squadRepository.squadLiveData

    init {
    }
    fun getSquad(team: String) {
        viewModelScope.launch(Dispatchers.IO) {
            squadRepository.getSquad(team)
        }
    }
}