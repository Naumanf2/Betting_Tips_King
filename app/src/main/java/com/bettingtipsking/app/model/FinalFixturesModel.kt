package com.bettingtipsking.app.model

import com.bettingtipsking.app.model.fixtures.Fixture
import com.bettingtipsking.app.model.fixtures.League

data class FinalFixturesModel(val league: League, val matches: List<FinalMatchesModel>) {

}