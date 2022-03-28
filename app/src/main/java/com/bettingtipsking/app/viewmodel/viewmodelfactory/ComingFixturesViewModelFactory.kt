package com.bettingtipsking.app.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.viewmodel.ComingFixturesViewModel

class ComingFixturesViewModelFactory(private val fixturesService: FixturesService) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ComingFixturesViewModel(fixturesService) as T
    }
}