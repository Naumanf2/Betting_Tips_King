package com.bettingtipsking.app.model.events

data class Response(
    val assist: Assist,
    val comments: Any,
    val detail: String,
    val player: Player,
    val team: Team,
    val time: Time,
    val type: String
)