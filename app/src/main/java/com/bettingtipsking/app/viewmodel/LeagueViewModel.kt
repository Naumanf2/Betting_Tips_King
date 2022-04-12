package com.bettingtipsking.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.Room.entity.FavouritLeaguesEntity
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.league.LeagueModel
import com.bettingtipsking.app.repository.LeagueRepository
import com.bettingtipsking.app.repository.LocalDBLeagueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeagueViewModel(private val fixturesService: FixturesService, application: Application) : AndroidViewModel(application) {

    private val leagueRepository = LeagueRepository(fixturesService);
    private val localDBLeagueRepository  = LocalDBLeagueRepository(application)

    val leaguesLiveData: LiveData<LeagueModel>
        get() = leagueRepository.leaguesLiveData

     fun getLeagues() {
        viewModelScope.launch(Dispatchers.IO) {
            leagueRepository.getLeagues()
        }
    }


    fun insertLocalFavouritLeague(favouritLeaguesEntity: FavouritLeaguesEntity?) {
        localDBLeagueRepository.insertFavouritLeague(favouritLeaguesEntity)
    }
    fun getAllLocalFavouritLeagues(): LiveData<List<FavouritLeaguesEntity?>?>? {
        return localDBLeagueRepository.allFavouritLeagues
    }
    fun deleteLocalFavouritLeague(favouritLeaguesEntity: FavouritLeaguesEntity?) {
        localDBLeagueRepository.deleteFavouritLeague(favouritLeaguesEntity)
    }

}