package com.bettingtipsking.app.model.predictions

data class Score(
    val extratime: Extratime,
    val fulltime: Fulltime,
    val halftime: Halftime,
    val penalty: Penalty
)