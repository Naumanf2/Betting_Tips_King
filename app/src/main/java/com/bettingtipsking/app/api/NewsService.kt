package com.bettingtipsking.app.api

import com.bettingtipsking.app.model.news.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/api/v2.0/news/fixtures")
    suspend fun getNews(
        @Query("api_token") api_token: String,
        @Query("page") page: Int
    ): retrofit2.Response<NewsModel>
}