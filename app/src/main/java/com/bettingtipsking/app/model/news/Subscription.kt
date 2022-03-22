package com.bettingtipsking.app.model.news

data class Subscription(
    val ends_at: EndsAt,
    val started_at: StartedAt,
    val trial_ends_at: TrialEndsAt
)