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
import com.revenco.eyepetizer_jetpack.utils.LogUtil
import com.revenco.eyepetizer_jetpack.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel : BaseViewModel() {
    private var uiState = MutableLiveData<HomeUiState>()
    private val url = "v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83"

    lateinit var sourceFactory: HandleHomeDataSourceFactory

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
            val homeData = HomeRepository().getHomeData(url)
            withContext(Dispatchers.Main)
            {
                if (homeData is RESULT.OnSuccess) {
                    sourceFactory =
                        HandleHomeDataSourceFactory(homeDataResp = homeData.data)
                    val config = PagedList.Config.Builder().setPageSize(2)
                        .setInitialLoadSizeHint(2).build()
                    val liveData = LivePagedListBuilder(sourceFactory, config).build()
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

    fun refresh() {
        LogUtil.i("我被刷新了")
        if (::sourceFactory.isInitialized) {
            LogUtil.i("我被刷新了1")
            sourceFactory.sourceLiveData.value?.invalidate()
            val config = PagedList.Config.Builder().setPageSize(2)
                .setInitialLoadSizeHint(2).build()
            val liveData = LivePagedListBuilder(sourceFactory, config).build()
            providerHomeUiState(showProgress = false, data = liveData)
        }else
        {
            getHomeData()
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
        val data: LiveData<PagedList<HomeDataResp.Issue.Item>>? = null,
        val errorMsg: String? = null,
        val errorCode: String? = null
    )

    inner class HandleHomeDataSourceFactory constructor(private val homeDataResp: HomeDataResp) :
        DataSource.Factory<String, HomeDataResp.Issue.Item>() {
        val sourceLiveData = MutableLiveData<ItemKeyedSubredditDataSource>()
        override fun create(): DataSource<String, HomeDataResp.Issue.Item> {
            val source = ItemKeyedSubredditDataSource(homeDataResp)
            sourceLiveData.postValue(source)
            return source
        }
    }
}