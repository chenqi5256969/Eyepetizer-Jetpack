package com.revenco.eyepetizer_jetpack.ui.fragment.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.revenco.eyepetizer_jetpack.adapters.DAILY_FRAGMENT_INDEX
import com.revenco.eyepetizer_jetpack.adapters.FIND_FRAGMENT_INDEX
import com.revenco.eyepetizer_jetpack.adapters.IndexFragmentAdapter
import com.revenco.eyepetizer_jetpack.adapters.RECOMMEND_FRAGMENT_INDEX
import com.revenco.eyepetizer_jetpack.databinding.FragmentIndexBinding
import com.revenco.eyepetizer_jetpack.utils.ktx.invokeTabLocation

class IndexFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIndexBinding.inflate(inflater, container, false)
        val tabLayoutView = binding.tabLayout
        val viewPager2View = binding.viewPager2
        tabLayoutView.invokeTabLocation()
        viewPager2View.adapter = IndexFragmentAdapter(this)

        TabLayoutMediator(tabLayoutView, viewPager2View) { tab: TabLayout.Tab, position: Int ->
            tab.text = getTabTitle(position)
        }.attach()
        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            FIND_FRAGMENT_INDEX -> "发现"
            RECOMMEND_FRAGMENT_INDEX -> "推荐"
            DAILY_FRAGMENT_INDEX -> "日常"
            else -> null
        }
    }

}
