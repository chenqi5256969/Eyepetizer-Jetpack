package com.revenco.eyepetizer_jetpack.ui.fragment.index

import androidx.lifecycle.Observer
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.adapters.IndexRecommendRecyclerAdapter
import com.revenco.eyepetizer_jetpack.ui.fragment.base.BaseFragment
import com.revenco.eyepetizer_jetpack.vm.HomeViewModel
import com.revenco.eyepetizer_jetpack.vm.base.InjectorUtils
import kotlinx.android.synthetic.main.fragment_index_recommend.*

class IndexRecommendFragment : BaseFragment<HomeViewModel>() {
    private lateinit var adapter: IndexRecommendRecyclerAdapter
    override fun providerVM(): HomeViewModel {
        return InjectorUtils.providerHomeViewModelFactory().create(HomeViewModel::class.java)
    }

    override fun initView() {
        adapter = IndexRecommendRecyclerAdapter()

    }

    override fun startObserver() {
        mViewModel.getHomeData()
        mViewModel.getUiState()
            .observe(viewLifecycleOwner,
                Observer<HomeViewModel.HomeUiState?> { t ->
                    if (t!!.showProgress) {
                        showProgress()
                    } else {
                        dismissProgress()
                    }
                    t.data?.also {
                        adapter.submitList(t.data.value)
                        indexRecommendRecycler.adapter = adapter
                    }
                })
    }

    override fun generateLayout(): Int {
        return R.layout.fragment_index_recommend
    }
}
