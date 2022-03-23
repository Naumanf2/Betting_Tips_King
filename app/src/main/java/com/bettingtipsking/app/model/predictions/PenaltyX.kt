package com.bettingtipsking.app.model.predictions

data class PenaltyX(
    val missed: Missed,
    val scored: Scored,
    val total: Int
)