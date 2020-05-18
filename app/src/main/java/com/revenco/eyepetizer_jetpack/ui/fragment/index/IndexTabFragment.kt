package com.revenco.eyepetizer_jetpack.ui.fragment.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.revenco.eyepetizer_jetpack.adapters.IndexFragmentAdapter
import com.revenco.eyepetizer_jetpack.adapters.IndexFragmentAdapter.Companion.DAILY_FRAGMENT_INDEX
import com.revenco.eyepetizer_jetpack.adapters.IndexFragmentAdapter.Companion.FIND_FRAGMENT_INDEX
import com.revenco.eyepetizer_jetpack.adapters.IndexFragmentAdapter.Companion.RECOMMEND_FRAGMENT_INDEX
import com.revenco.eyepetizer_jetpack.databinding.FragmentIndexTabBinding
import com.revenco.eyepetizer_jetpack.ktx.invokeTabLocation

class IndexTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIndexTabBinding.inflate(inflater, container, false)
        val tabLayoutView = binding.tabLayout
        val viewPager2View = binding.viewPager2
        tabLayoutView.invokeTabLocation()

        viewPager2View.adapter = IndexFragmentAdapter(this)

        TabLayoutMediator(tabLayoutView, viewPager2View) { tab: TabLayout.Tab, position: Int ->
            tab.text = getTabTitle(position)
        }.attach()

        /*   binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
               val offset = abs(verticalOffset)
               val maxOffset = appBarLayout!!.totalScrollRange
               if (offset == 0) {
                   toolbar.visibility = View.GONE
               } else if (offset <= maxOffset) {
                   toolbar.setBackgroundColor(Color.parseColor("#000000"))
                   toolbar.visibility = View.VISIBLE
                   val alpha = (offset * 1f) / (maxOffset * 1f)
                   toolbar.alpha = alpha

               }
           })*/
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
