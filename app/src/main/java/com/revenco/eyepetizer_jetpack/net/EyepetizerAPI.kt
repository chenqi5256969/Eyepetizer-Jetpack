package com.revenco.eyepetizer_jetpack.net

import com.revenco.eyepetizer_jetpack.net.bean.resp.HomeDataResp
import retrofit2.http.GET
import retrofit2.http.Url


interface EyepetizerAPI {
    @GET
    suspend fun getHomeData(@Url url: String): HomeDataResp
}