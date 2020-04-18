package com.revenco.eyepetizer_jetpack.repository

import com.revenco.eyepetizer_jetpack.net.RetrofitClient
import com.revenco.eyepetizer_jetpack.net.bean.resp.HomeDataResp
import com.revenco.eyepetizer_jetpack.net.bean.resp.base.RESULT

class HomeRepository : BaseRepository() {
    suspend fun getHomeData(): RESULT<HomeDataResp> {
        return handleHomeResponse()
    }

    private suspend fun handleHomeResponse(): RESULT<HomeDataResp> {
        return handleResponse(RetrofitClient.getInstance().createApi().getHomeData())
    }
}