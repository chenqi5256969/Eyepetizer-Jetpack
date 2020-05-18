package com.revenco.eyepetizer_jetpack.repository

import androidx.paging.ItemKeyedDataSource
import com.revenco.eyepetizer_jetpack.net.bean.resp.IndexDailyResp
import com.revenco.eyepetizer_jetpack.net.bean.resp.base.RESULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MultiItemKeyedSubredditDataSource constructor(private var homeDataResp: IndexDailyResp) :
    ItemKeyedDataSource<String, IndexDailyResp.Issue.Item>() {
    private var itemList = homeDataResp.issueList[0].itemList

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<IndexDailyResp.Issue.Item>
    ) {
        callback.onResult(itemList)
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<IndexDailyResp.Issue.Item>
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            val data = IndexRepository().getIndexDailyData(homeDataResp.nextPageUrl)
            withContext(Dispatchers.Main)
            {
                if (data is RESULT.OnSuccess) {
                    var moreItemList = data.data.issueList[0].itemList
                    callback.onResult(moreItemList)
                    homeDataResp = data.data
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<IndexDailyResp.Issue.Item>
    ) {

    }

    override fun getKey(item: IndexDailyResp.Issue.Item): String {
        return item.id.toString()
    }
}