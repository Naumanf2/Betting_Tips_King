package com.bettingtipsking.app.api

import com.bettingtipsking.app.model.news.NewsModel
import com.bettingtipsking.app.model.predictions.PredictionsModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MatchesService {

    @Headers(
        value = ["content-type: application/json",
            "x-rapidapi-host: api-football-v1.p.rapidapi.com",
            "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"]
    )
    @GET("/v3/predictions")
     fun getPredictions(@Query("fixture") fixture: Int): retrofit2.Response<PredictionsModel>
}