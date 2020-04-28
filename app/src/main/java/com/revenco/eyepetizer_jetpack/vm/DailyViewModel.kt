package com.revenco.eyepetizer_jetpack.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
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


class DailyViewModel : BaseViewModel() {
    private var uiState = MutableLiveData<DailyUiState>()
    private val url = "v2/feed?date=1587949200000&num=1"

    lateinit var sourceFactory: HandleDailyDataSourceFactory

    fun getUiState(): MutableLiveData<DailyUiState> {
        return uiState
    }

    fun getDailyData() {
        viewModelScope.launch(Dispatchers.IO)
        {
            withContext(Dispatchers.Main)
            {
                providerDailyUiState(showProgress = true)
            }
            val homeData = HomeRepository().getHomeData(url)
            withContext(Dispatchers.Main)
            {
                if (homeData is RESULT.OnSuccess) {
                    sourceFactory =
                        HandleDailyDataSourceFactory(homeDataResp = homeData.data)
                    val config = PagedList.Config.Builder().setPageSize(2)
                        .setInitialLoadSizeHint(2).build()
                    val liveData = LivePagedListBuilder(sourceFactory, config).build()
                    providerDailyUiState(showProgress = false, data = liveData)
                } else if (homeData is RESULT.OnError) {
                    providerDailyUiState(
                        showProgress = false,
                        errorCode = homeData.errcode,
                        errorMsg = homeData.errMsg
                    )
                }
            }

        }
    }

    fun refresh() {
        if (::sourceFactory.isInitialized) {
            sourceFactory.sourceLiveData.value?.invalidate()
            val config = PagedList.Config.Builder().setPageSize(2)
                .setInitialLoadSizeHint(2).build()
            val liveData = LivePagedListBuilder(sourceFactory, config).build()
            providerDailyUiState(showProgress = false, data = liveData)
        } else {
            getDailyData()
        }
    }

    private fun providerDailyUiState(
        showProgress: Boolean = true,
        data: LiveData<PagedList<HomeDataResp.Issue.Item>>? = null,
        errorMsg: String? = null,
        errorCode: String? = null
    ) {
        val dailyUiState = DailyUiState(showProgress, data, errorMsg, errorCode)
        uiState.value = dailyUiState
    }

    data class DailyUiState constructor(
        val showProgress: Boolean,
        val data: LiveData<PagedList<HomeDataResp.Issue.Item>>? = null,
        val errorMsg: String? = null,
        val errorCode: String? = null
    )

    inner class HandleDailyDataSourceFactory constructor(private val homeDataResp: HomeDataResp) :
        DataSource.Factory<String, HomeDataResp.Issue.Item>() {
        val sourceLiveData = MutableLiveData<ItemKeyedSubredditDataSource>()
        override fun create(): DataSource<String, HomeDataResp.Issue.Item> {
            val source = ItemKeyedSubredditDataSource(homeDataResp)
            sourceLiveData.postValue(source)
            return source
        }
    }
}