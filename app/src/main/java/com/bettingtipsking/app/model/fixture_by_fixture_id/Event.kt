package com.bettingtipsking.app.model.fixture_by_fixture_id

data class Event(
    val assist: Assist,
    val comments: Any,
    val detail: String,
    val player: Player,
    val team: Team,
    val time: Time,
    val type: String
)