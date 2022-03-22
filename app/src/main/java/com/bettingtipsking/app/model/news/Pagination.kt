package com.bettingtipsking.app.model.news

data class Pagination(
    val count: Int,
    val current_page: Int,
    val links: Links,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)