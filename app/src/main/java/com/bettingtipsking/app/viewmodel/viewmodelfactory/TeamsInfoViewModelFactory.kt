package com.bettingtipsking.app.viewmodel.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.viewmodel.TeamsInfoViewModel

class TeamsInfoViewModelFactory (private val fixturesService: FixturesService, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TeamsInfoViewModel(fixturesService,application) as T
    }
}