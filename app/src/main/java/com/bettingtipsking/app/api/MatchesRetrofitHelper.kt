package com.bettingtipsking.app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MatchesRetrofitHelper {

    private const val BASE_URL = "https://api-football-v1.p.rapidapi.com"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}