package com.bettingtipsking.app.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.viewmodel.H2HViewModel

class H2HModelFactory (private val fixturesService: FixturesService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return H2HViewModel(fixturesService) as T
    }
}