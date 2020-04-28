package com.revenco.eyepetizer_jetpack.ui.fragment.index

import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.ui.fragment.base.BaseFragment
import com.revenco.eyepetizer_jetpack.vm.DailyViewModel
import com.revenco.eyepetizer_jetpack.vm.base.InjectorUtils
import com.revenco.eyepetizer_jetpack.widget.LoadingHeadView
import kotlinx.android.synthetic.main.fragment_index_daily.*


class IndexDailyFragment : BaseFragment<DailyViewModel>() {
    override fun providerVM(): DailyViewModel {
        return InjectorUtils.providerDailyViewModelFactory().create(DailyViewModel::class.java)
    }

    override fun initView() {
        dailyRefreshLayout.autoRefresh()
        dailyRefreshLayout.setEnableLoadMore(false)
        dailyRefreshLayout.setRefreshHeader(LoadingHeadView(activity!!))
        dailyRefreshLayout.setHeaderHeight(100f)
        dailyRefreshLayout.setOnRefreshListener {
            mViewModel.refresh()
        }
    }

    override fun startObserver() {
        mViewModel.getDailyData()

    }

    override fun generateLayout(): Int {

        return R.layout.fragment_index_daily
    }
}