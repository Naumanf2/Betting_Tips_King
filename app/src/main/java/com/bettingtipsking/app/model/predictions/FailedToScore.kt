package com.bettingtipsking.app.model.predictions

data class FailedToScore(
    val away: Int,
    val home: Int,
    val total: Int
)