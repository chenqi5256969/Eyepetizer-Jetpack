package com.revenco.eyepetizer_jetpack.repository

import androidx.paging.ItemKeyedDataSource
import com.revenco.eyepetizer_jetpack.net.bean.resp.HomeDataResp


class ItemKeyedSubredditDataSource constructor(val itemList: List<HomeDataResp.Issue.Item>) :
    ItemKeyedDataSource<String, HomeDataResp.Issue.Item>() {
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<HomeDataResp.Issue.Item>
    ) {

    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<HomeDataResp.Issue.Item>
    ) {
        callback.onResult(itemList)
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