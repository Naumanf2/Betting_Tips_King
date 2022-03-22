package com.bettingtipsking.app.model.news

data class Data(
    val created_at: String,
    val fixture_id: Int,
    val introduction: String,
    val localteam: String,
    val type: String,
    val updated_at: String,
    val visitorteam: String
)