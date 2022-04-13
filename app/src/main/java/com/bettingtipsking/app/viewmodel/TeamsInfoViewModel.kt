package com.bettingtipsking.app.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.league.LeagueModel
import com.bettingtipsking.app.model.teamsInfo.TeamsInfoModel
import com.bettingtipsking.app.repository.LeagueRepository
import com.bettingtipsking.app.repository.MatchesNotificationRepository
import com.bettingtipsking.app.repository.TeamsInfoRepository
import com.bettingtipsking.app.room.entity.MatchesNotificationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamsInfoViewModel (private val fixturesService: FixturesService, private val application: Application) : ViewModel() {

    private val teamsInfoRepository= TeamsInfoRepository(fixturesService)
    private val matchesNotificationRepository  = MatchesNotificationRepository(application)


    val data: LiveData<TeamsInfoModel>
        get() = teamsInfoRepository.data

    init {
    }

    public fun getMatches(id: Int , season : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            teamsInfoRepository.getTeamsInfo(id, season)
        }
    }



    fun insertNotifiedMatches(matchesNotificationEntity: MatchesNotificationEntity?) {
        matchesNotificationRepository.insertMatchNotification(matchesNotificationEntity)
    }
    fun getAllNotifiedMatches(): LiveData<List<MatchesNotificationEntity?>?>? {
        return matchesNotificationRepository.allFavouriteLeagues;
    }
    fun deleteNotifiedMatches(matchesNotificationEntity: MatchesNotificationEntity??) {
        matchesNotificationRepository.deleteNotifiedMatches(matchesNotificationEntity)
    }
}