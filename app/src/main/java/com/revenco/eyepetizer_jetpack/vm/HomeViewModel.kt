package com.revenco.eyepetizer_jetpack.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.revenco.eyepetizer_jetpack.net.bean.resp.HomeDataResp
import com.revenco.eyepetizer_jetpack.net.bean.resp.base.RESULT
import com.revenco.eyepetizer_jetpack.repository.HomeRepository
import com.revenco.eyepetizer_jetpack.repository.ItemKeyedSubredditDataSource
import com.revenco.eyepetizer_jetpack.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel : BaseViewModel() {
    private var uiState = MutableLiveData<HomeUiState>()

    fun getUiState(): MutableLiveData<HomeUiState> {
        return uiState
    }

    fun getHomeData() {
        viewModelScope.launch(Dispatchers.IO)
        {
            withContext(Dispatchers.Main)
            {
                providerHomeUiState(showProgress = true)
            }

            val homeData = HomeRepository().getHomeData()

            withContext(Dispatchers.Main)
            {
                if (homeData is RESULT.OnSuccess) {
                    val sourceFactory =
                        HandleHomeDataSourceFactory(homeData.data.issueList[0].itemList)
                    val liveData = LivePagedListBuilder(sourceFactory, 2).build()
                    providerHomeUiState(showProgress = false, data = liveData)
                } else if (homeData is RESULT.OnError) {
                    providerHomeUiState(
                        showProgress = false,
                        errorCode = homeData.errcode,
                        errorMsg = homeData.errMsg
                    )
                }
            }

        }
    }

    private fun providerHomeUiState(
        showProgress: Boolean = true,
        data: LiveData<PagedList<HomeDataResp.Issue.Item>>? = null,
        errorMsg: String? = null,
        errorCode: String? = null
    ) {
        val homeUiState = HomeUiState(showProgress, data, errorMsg, errorCode)
        uiState.value = homeUiState
    }

    data class HomeUiState constructor(
        val showProgress: Boolean,
        val data: LiveData<PagedList<HomeDataResp.Issue.Item>>?,
        val errorMsg: String?,
        val errorCode: String?
    )

    inner class HandleHomeDataSourceFactory constructor(val itemList: List<HomeDataResp.Issue.Item>) :
        DataSource.Factory<String, HomeDataResp.Issue.Item>() {
        private val sourceLiveData =
            MutableLiveData<ItemKeyedDataSource<String, HomeDataResp.Issue.Item>>()

        override fun create(): DataSource<String, HomeDataResp.Issue.Item> {
            val dataSource = ItemKeyedSubredditDataSource(itemList)
            sourceLiveData.postValue(dataSource)
            return dataSource
        }
    }
}