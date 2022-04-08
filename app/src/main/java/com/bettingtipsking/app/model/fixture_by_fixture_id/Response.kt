package com.bettingtipsking.app.model.fixture_by_fixture_id

data class Response(
    val events: List<Event>,
    val fixture: Fixture,
    val goals: Goals,
    val league: League,
    val lineups: List<Any>,
    val players: List<Any>,
    val score: Score,
    val statistics: List<Any>,
    val teams: Teams
)