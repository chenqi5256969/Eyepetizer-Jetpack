package com.revenco.eyepetizer_jetpack.net

import com.revenco.eyepetizer_jetpack.net.bean.resp.HomeDataResp
import retrofit2.http.GET


interface EyepetizerAPI {

    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    suspend fun getHomeData(): HomeDataResp
}