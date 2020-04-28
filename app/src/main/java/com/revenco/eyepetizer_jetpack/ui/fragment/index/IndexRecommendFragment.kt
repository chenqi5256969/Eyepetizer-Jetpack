package com.revenco.eyepetizer_jetpack.ui.fragment.index

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.adapters.IndexRecommendRecyclerAdapter
import com.revenco.eyepetizer_jetpack.ui.fragment.base.BaseFragment
import com.revenco.eyepetizer_jetpack.vm.HomeViewModel
import com.revenco.eyepetizer_jetpack.vm.base.InjectorUtils
import com.revenco.eyepetizer_jetpack.widget.LoadingHeadView
import kotlinx.android.synthetic.main.fragment_index_recommend.*

class IndexRecommendFragment : BaseFragment<HomeViewModel>() {
    private lateinit var adapter: IndexRecommendRecyclerAdapter

    override fun providerVM(): HomeViewModel {
        return InjectorUtils.providerHomeViewModelFactory().create(HomeViewModel::class.java)
    }

    override fun initView() {
        val viewPager2 = activity!!.findViewById<ViewPager2>(R.id.viewPager2)
        adapter = IndexRecommendRecyclerAdapter(viewPager2)

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
                Observer<HomeViewModel.HomeUiState?> { t ->
                    t?.data?.also {
                        recommendRefreshLayout.finishRefresh(true)
                        t.data.observe(viewLifecycleOwner, Observer {
                            adapter.submitList(it)
                        })
                    }

                    t?.errorMsg?.also {
                        Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show()
                        recommendRefreshLayout.finishRefresh(false)
                    }
                })
        indexRecommendRecycler.adapter = adapter
    }

    override fun generateLayout(): Int {
        return R.layout.fragment_index_recommend
    }
}
