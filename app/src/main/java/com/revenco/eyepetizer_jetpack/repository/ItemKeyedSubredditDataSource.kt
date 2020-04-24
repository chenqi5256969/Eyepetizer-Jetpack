package com.revenco.eyepetizer_jetpack.repository

import androidx.paging.ItemKeyedDataSource
import com.revenco.eyepetizer_jetpack.net.bean.resp.HomeDataResp
import com.revenco.eyepetizer_jetpack.net.bean.resp.base.RESULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemKeyedSubredditDataSource constructor(private var homeDataResp: HomeDataResp) :
    ItemKeyedDataSource<String, HomeDataResp.Issue.Item>() {
    private var itemList = homeDataResp.issueList[0].itemList

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<HomeDataResp.Issue.Item>
    ) {
        itemList = itemList.drop(1)
        callback.onResult(itemList)
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<HomeDataResp.Issue.Item>
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            val data = HomeRepository().getHomeData(homeDataResp.nextPageUrl)
            withContext(Dispatchers.Main)
            {
                if (data is RESULT.OnSuccess) {
                    var moreItemList = data.data.issueList[0].itemList
                    moreItemList = moreItemList.drop(1)
                    callback.onResult(moreItemList)
                    homeDataResp = data.data
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<HomeDataResp.Issue.Item>
    ) {

    }

    override fun getKey(item: HomeDataResp.Issue.Item): String {
        return item.id.toString()
    }
}