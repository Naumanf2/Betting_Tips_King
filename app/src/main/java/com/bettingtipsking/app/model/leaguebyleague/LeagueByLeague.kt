package com.bettingtipsking.app.model.leaguebyleague

data class LeagueByLeague(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)