package com.revenco.eyepetizer_jetpack.net

import com.google.gson.JsonObject
import com.revenco.eyepetizer_jetpack.net.bean.resp.IndexDailyResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface EyepetizerAPI {

    @GET
    suspend fun getIndexDailyData(@Url url: String): IndexDailyResp

    @GET
     fun getIndexRecommendData(@Url url: String): Call<JsonObject>
}