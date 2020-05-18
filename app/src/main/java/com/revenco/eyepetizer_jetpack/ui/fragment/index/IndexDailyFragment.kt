package com.revenco.eyepetizer_jetpack.ui.fragment.index

import android.widget.Toast
import androidx.lifecycle.Observer
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.adapters.IndexDailyRecyclerAdapter
import com.revenco.eyepetizer_jetpack.ui.fragment.base.BaseFragment
import com.revenco.eyepetizer_jetpack.vm.base.InjectorUtils
import com.revenco.eyepetizer_jetpack.vm.index.IndexDailyViewModel
import com.revenco.eyepetizer_jetpack.widget.LoadingHeadView
import kotlinx.android.synthetic.main.fragment_index_daily.*


class IndexDailyFragment : BaseFragment<IndexDailyViewModel>() {
    private lateinit var dailyAdapter: IndexDailyRecyclerAdapter

    override fun providerVM(): IndexDailyViewModel {
        return InjectorUtils.providerDailyViewModelFactory().create(IndexDailyViewModel::class.java)
    }

    override fun initView() {
        dailyAdapter = IndexDailyRecyclerAdapter()
        dailyRefreshLayout.autoRefresh()
        dailyRefreshLayout.setEnableLoadMore(false)
        dailyRefreshLayout.setRefreshHeader(LoadingHeadView(activity!!))
        dailyRefreshLayout.setHeaderHeight(100f)
        dailyRefreshLayout.setOnRefreshListener {
            mViewModel.refresh()
        }
    }

    override fun startObserver() {
        mViewModel.getUiState()
            .observe(viewLifecycleOwner,
                Observer<IndexDailyViewModel.DailyUiState?> { t ->
                    t?.data?.also {
                        dailyRefreshLayout.finishRefresh(true)
                        t.data.observe(viewLifecycleOwner, Observer {
                            dailyAdapter.submitList(it)
                        })
                    }

                    t?.errorMsg?.also {
                        Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show()
                        dailyRefreshLayout.finishRefresh(false)
                    }
                })
        dailyRecycler.adapter = dailyAdapter
    }

    override fun generateLayout(): Int {
        return R.layout.fragment_index_daily
    }
}