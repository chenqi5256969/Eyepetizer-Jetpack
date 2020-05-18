package com.revenco.eyepetizer_jetpack.vm.index

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.revenco.eyepetizer_jetpack.net.bean.resp.IndexDailyResp
import com.revenco.eyepetizer_jetpack.net.bean.resp.base.RESULT
import com.revenco.eyepetizer_jetpack.repository.IndexRepository
import com.revenco.eyepetizer_jetpack.repository.MultiItemKeyedSubredditDataSource
import com.revenco.eyepetizer_jetpack.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IndexDailyViewModel : BaseViewModel() {
    private var uiState = MutableLiveData<DailyUiState>()
    private val url = "v2/feed?date=1587949200000&num=1"

    private lateinit var sourceFactory: HandleDailyDataSourceFactory

    fun getUiState(): MutableLiveData<DailyUiState> {
        return uiState
    }

    private fun getIndexDailyData() {
        viewModelScope.launch(Dispatchers.IO)
        {
            withContext(Dispatchers.Main)
            {
                providerDailyUiState(showProgress = true)
            }
            val homeData = IndexRepository().getIndexDailyData(url)
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
            getIndexDailyData()
        }
    }

    private fun providerDailyUiState(
        showProgress: Boolean = true,
        data: LiveData<PagedList<IndexDailyResp.Issue.Item>>? = null,
        errorMsg: String? = null,
        errorCode: String? = null
    ) {
        val dailyUiState = DailyUiState(
            showProgress,
            data,
            errorMsg,
            errorCode
        )
        uiState.value = dailyUiState
    }

    data class DailyUiState constructor(
        val showProgress: Boolean,
        val data: LiveData<PagedList<IndexDailyResp.Issue.Item>>? = null,
        val errorMsg: String? = null,
        val errorCode: String? = null
    )

    inner class HandleDailyDataSourceFactory constructor(private val homeDataResp: IndexDailyResp) :
        DataSource.Factory<String, IndexDailyResp.Issue.Item>() {
        val sourceLiveData = MutableLiveData<MultiItemKeyedSubredditDataSource>()
        override fun create(): DataSource<String, IndexDailyResp.Issue.Item> {
            val source = MultiItemKeyedSubredditDataSource(homeDataResp)
            sourceLiveData.postValue(source)
            return source
        }
    }
}