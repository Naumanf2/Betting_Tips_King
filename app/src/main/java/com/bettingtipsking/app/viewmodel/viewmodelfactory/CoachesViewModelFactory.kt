package com.bettingtipsking.app.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.repository.CoachesRepository
import com.bettingtipsking.app.viewmodel.CoachesViewModel

class CoachesViewModelFactory(private val fixturesService: FixturesService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoachesViewModel(fixturesService) as T
    }
}