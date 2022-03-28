package com.bettingtipsking.app.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.repository.FixturesByDateRepository
import com.bettingtipsking.app.viewmodel.CoachesViewModel
import com.bettingtipsking.app.viewmodel.FixturesByDateViewModel

class FixturesByDateViewModelFactory(private val fixturesByDateRepository: FixturesByDateRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FixturesByDateViewModel(fixturesByDateRepository) as T
    }
}