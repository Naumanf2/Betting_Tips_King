package com.bettingtipsking.app.api

import com.bettingtipsking.app.activity.fixtures.model.EventsModelold
import com.bettingtipsking.app.model.coach.CoachesModel
import com.bettingtipsking.app.model.events.EventsModel
import com.bettingtipsking.app.model.fixture_by_fixture_id.FixtureByFixtureId
import com.bettingtipsking.app.model.fixtures.FixturesModel
import com.bettingtipsking.app.model.league.LeagueModel
import com.bettingtipsking.app.model.predictions.PredictionsModel
import com.bettingtipsking.app.model.squad.SquadModel
import com.bettingtipsking.app.model.teamsInfo.TeamsInfoModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FixturesService {

    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/fixtures")
    suspend fun getLiveMatches(@Query("live") live: String): retrofit2.Response<FixturesModel>

    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/fixtures")
    suspend fun getFixturesByDate(@Query("date") date: String): retrofit2.Response<FixturesModel>


    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/fixtures")
    suspend fun getFixturesByFixtureId(@Query("id") id: Int): retrofit2.Response<FixtureByFixtureId>

    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/fixtures/headtohead")
    suspend fun getHeadToHeadBetweenTwoTeams(@Query("h2h") h2h: String): retrofit2.Response<FixturesModel>

    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/fixtures/headtohead")
    suspend fun getHeadToHeadBetweenTwoTeamsByDate(
        @Query("h2h") h2h: String,
        @Query("date") date: String
    ): retrofit2.Response<FixturesModel>

    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/predictions")
    suspend fun getPredictions(@Query("fixture") fixture: Int): retrofit2.Response<PredictionsModel>


    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/fixtures/events")
    suspend fun getEventsByFixture(@Query("fixture") fixture: Int): retrofit2.Response<EventsModel>


    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/fixtures/events")
    suspend fun getEventsByTeam(
        @Query("fixture") fixture: Int,
        @Query("team") id: Int
    ): retrofit2.Response<EventsModel>


    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/coachs")
    suspend fun getCoach(@Query("team") team: Int): retrofit2.Response<CoachesModel>


    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/players/squads")
    suspend fun getSquad(@Query("team") team: String): retrofit2.Response<SquadModel>


    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/leagues")
    suspend fun getLeague(): retrofit2.Response<LeagueModel>

    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/leagues")
    suspend fun getLeagueById(@Query("id") id: Int): retrofit2.Response<LeagueModel>

    @Headers(value = ["content-type: application/json", "x-rapidapi-host: api-football-v1.p.rapidapi.com", "x-rapidapi-key: 1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f"])
    @GET("/v3/teams")
    suspend fun getTeamsInfo(
        @Query("league") id: Int,
        @Query("season") season: Int
    ): retrofit2.Response<TeamsInfoModel>
}
