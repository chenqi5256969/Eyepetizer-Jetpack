package com.revenco.eyepetizer_jetpack.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.revenco.eyepetizer_jetpack.ui.fragment.index.IndexDailyFragment
import com.revenco.eyepetizer_jetpack.ui.fragment.index.IndexFindFragment
import com.revenco.eyepetizer_jetpack.ui.fragment.index.IndexRecommendFragment
import okio.ArrayIndexOutOfBoundsException

const val FIND_FRAGMENT_INDEX = 0
const val RECOMMEND_FRAGMENT_INDEX = 1
const val DAILY_FRAGMENT_INDEX = 2

class IndexFragmentAdapter constructor(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentCreator: Map<Int, () -> Fragment> = mapOf(
        FIND_FRAGMENT_INDEX to { IndexFindFragment() },
        RECOMMEND_FRAGMENT_INDEX to { IndexRecommendFragment() },
        DAILY_FRAGMENT_INDEX to { IndexDailyFragment() }
    )

    override fun getItemCount(): Int {
        return tabFragmentCreator?.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreator[position]?.invoke()
            ?: throw  ArrayIndexOutOfBoundsException("你的fragment越界了")
    }
}