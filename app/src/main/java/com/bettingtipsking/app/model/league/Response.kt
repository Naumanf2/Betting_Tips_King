package com.bettingtipsking.app.model.league

data class Response(
    val country: Country,
    val league: League,
    val seasons: List<Season>
)