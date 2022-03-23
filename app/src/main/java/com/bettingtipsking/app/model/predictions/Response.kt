package com.bettingtipsking.app.model.predictions

data class Response(
    val comparison: Comparison,
    val h2h: List<H2hX>,
    val league: LeagueX,
    val predictions: Predictions,
    val teams: TeamsX
)