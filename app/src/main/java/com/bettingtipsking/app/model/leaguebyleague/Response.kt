package com.bettingtipsking.app.model.leaguebyleague

data class Response(
    val country: Country,
    val league: League,
    val seasons: List<Season>
)