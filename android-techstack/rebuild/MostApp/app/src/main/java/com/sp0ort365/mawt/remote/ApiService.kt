package com.sp0ort365.mawt.remote

import com.sp0ort365.mawt.remote.models.bet.MainGameEvent
import com.sp0ort365.mawt.remote.models.bet.MainMatchDetailResponse
import com.sp0ort365.mawt.remote.models.bet.MainMatchResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {


    @GET("v1/events/{sport_id}/0/list/150/live/en")
    suspend fun getLiveMatchList(
        @Path("sport_id") sportId: String
    ): Response<MainMatchResponse>

    @GET("https://cdn.incub.space/v1/opp/icon/{icon_id}.png")
    suspend fun getTeamIcon(
        @Path("icon_id") id: String
    ): Response<ResponseBody>

    @GET("/v1/event/{match_id}/list/live/en")
    suspend fun getSpecificMatch(
        @Path("match_id") id:String
    ) :Response<MainMatchDetailResponse>


    @GET("v1/events/{sport_id}/0/list/150/line/en")
    suspend fun getUpcomingMatchList(
        @Path("sport_id") sportId: String
    ): Response<MainMatchResponse>

    @GET("/v1/play/{match_id}/en")
    suspend fun getSpecificGameEvent(
        @Path("match_id") id:String
    ) :Response<MainGameEvent>

}