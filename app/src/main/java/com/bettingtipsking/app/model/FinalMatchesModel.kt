package com.bettingtipsking.app.model

import com.bettingtipsking.app.model.fixtures.*

data class FinalMatchesModel(
    val fixture: Fixture,
    val league: League,
    val goals: Goals,
    val score: Score,
    val teams: Teams) {

}