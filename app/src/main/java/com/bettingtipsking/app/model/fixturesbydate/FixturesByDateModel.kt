package com.bettingtipsking.app.model.fixturesbydate

data class FixturesByDateModel(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)