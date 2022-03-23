package com.bettingtipsking.app.model.predictions

data class Predictions(
    val advice: String,
    val goals: GoalsXX,
    val percent: Percent,
    val under_over: Any,
    val win_or_draw: Boolean,
    val winner: Winner
)