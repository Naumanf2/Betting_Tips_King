package com.bettingtipsking.app.model.news

data class Meta(
    val add_ons: List<AddOn>,
    val pagination: Pagination,
    val plans: List<Plan>,
    val sports: List<Sport>,
    val subscription: Subscription
)