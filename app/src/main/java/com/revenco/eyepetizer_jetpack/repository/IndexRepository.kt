package com.revenco.eyepetizer_jetpack.repository

import com.google.gson.JsonObject
import com.revenco.eyepetizer_jetpack.net.RetrofitClient
import com.revenco.eyepetizer_jetpack.net.bean.resp.IndexDailyResp
import com.revenco.eyepetizer_jetpack.net.bean.resp.base.RESULT
import retrofit2.Call

class IndexRepository : BaseRepository() {

    /**
     * 获取首页 日报 数据
     */
    suspend fun getIndexDailyData(url: String): RESULT<IndexDailyResp> {
        return safeApiCall {
            handleHomeResponse(
                RetrofitClient.getInstance().createApi().getIndexDailyData(url)
            )
        }
    }

    /**
     * 获取首页 推荐 数据
     */
    fun getIndexRecommendData(url: String): Call<JsonObject> {
        return RetrofitClient.getInstance().createApi().getIndexRecommendData(url)
    }

    private suspend fun <T : Any> handleHomeResponse(response: T): RESULT<T> {
        return handleResponse(response)
    }
}