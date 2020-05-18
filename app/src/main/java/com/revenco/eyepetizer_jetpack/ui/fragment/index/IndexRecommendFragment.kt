package com.revenco.eyepetizer_jetpack.ui.fragment.index

import android.widget.Toast
import androidx.lifecycle.Observer
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.adapters.IndexRecommendRecyclerAdapter
import com.revenco.eyepetizer_jetpack.ui.fragment.base.BaseFragment
import com.revenco.eyepetizer_jetpack.vm.base.InjectorUtils
import com.revenco.eyepetizer_jetpack.vm.index.IndexRecommendViewModel
import com.revenco.eyepetizer_jetpack.widget.LoadingHeadView
import kotlinx.android.synthetic.main.fragment_index_recommend.*

class IndexRecommendFragment : BaseFragment<IndexRecommendViewModel>() {
    private lateinit var adapter: IndexRecommendRecyclerAdapter

    override fun providerVM(): IndexRecommendViewModel {
        return InjectorUtils.providerHomeViewModelFactory()
            .create(IndexRecommendViewModel::class.java)
    }

    override fun initView() {
        recommendRefreshLayout.autoRefresh()
        recommendRefreshLayout.setEnableLoadMore(false)
        recommendRefreshLayout.setRefreshHeader(LoadingHeadView(activity!!))
        recommendRefreshLayout.setHeaderHeight(100f)
        recommendRefreshLayout.setOnRefreshListener {
            mViewModel.refresh()
        }
    }

    override fun startObserver() {
        mViewModel.getUiState()
            .observe(viewLifecycleOwner,
                Observer<IndexRecommendViewModel.HomeUiState?> { t ->
                    t?.json?.also { json ->
                        recommendRefreshLayout.finishRefresh(true)
                        //处理json
                        adapter = IndexRecommendRecyclerAdapter(json)
                        indexRecommendRecycler.adapter = adapter
                    }

                    t?.errorMsg?.also {
                        Toast.makeText(activity, t.errorMsg, Toast.LENGTH_SHORT).show()
                        recommendRefreshLayout.finishRefresh(false)
                    }
                })

    }

    override fun generateLayout(): Int {
        return R.layout.fragment_index_recommend
    }
}
