package com.bettingtipsking.app.model.news

data class Plan(
    val features: String,
    val name: String,
    val price_monthly: Int,
    val price_yearly: Int,
    val request_limit: String,
    val sport: String
)