package com.revenco.eyepetizer_jetpack.vm.index

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.revenco.eyepetizer_jetpack.repository.IndexRepository
import com.revenco.eyepetizer_jetpack.vm.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndexRecommendViewModel : BaseViewModel() {
    private var uiState = MutableLiveData<HomeUiState>()
    private val url = "v5/index/tab/allRec"

    fun getUiState(): MutableLiveData<HomeUiState> {
        return uiState
    }


    private fun getIndexRecommendData() {
        IndexRepository().getIndexRecommendData(url).enqueue(object : Callback<JsonObject?> {
            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                providerHomeUiState(
                    showProgress = false,
                    errorCode = "4",
                    errorMsg = t!!.message
                )
            }

            override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
                providerHomeUiState(
                    showProgress = false,
                    json = response.body().toString()
                )
            }

        })
    }

    fun refresh() {
        getIndexRecommendData()
    }

    private fun providerHomeUiState(
        showProgress: Boolean = true,
        json: String? = null,
        errorMsg: String? = null,
        errorCode: String? = null
    ) {
        val homeUiState =
            HomeUiState(
                showProgress,
                json,
                errorMsg,
                errorCode
            )
        uiState.value = homeUiState
    }

    data class HomeUiState constructor(
        val showProgress: Boolean,
        val json: String? = null,
        val errorMsg: String? = null,
        val errorCode: String? = null
    )
}