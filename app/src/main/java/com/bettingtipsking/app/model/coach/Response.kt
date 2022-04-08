package com.bettingtipsking.app.model.coach

data class Response(
    val age: Int,
    val birth: Birth,
    val career: List<Career>,
    val firstname: String,
    val height: Any,
    val id: Int,
    val lastname: String,
    val name: String,
    val nationality: String,
    val photo: String,
    val team: TeamX,
    val weight: Any
)

