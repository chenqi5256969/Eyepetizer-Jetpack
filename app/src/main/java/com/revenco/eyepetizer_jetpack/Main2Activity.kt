package com.revenco.eyepetizer_jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.revenco.eyepetizer_jetpack.ui.fragment.community.CommunityFragment
import com.revenco.eyepetizer_jetpack.ui.fragment.index.IndexFragment
import com.revenco.eyepetizer_jetpack.ui.fragment.my.MyFragment
import com.revenco.eyepetizer_jetpack.ui.fragment.notification.NotificationFragment
import com.revenco.eyepetizer_jetpack.ui.fragment.publish.PublishFragment
import com.revenco.eyepetizer_jetpack.utils.StatusBarUtil
import com.revenco.eyepetizer_jetpack.vm.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    companion object {
        const val INDEX = 0
        const val COMMUNITY = 1
        const val PUBLISH = 2
        const val NOTIFICATION = 3
        const val MY = 4
    }

    private val fragmentCreator: Map<Int, () -> Fragment> = mapOf(
        INDEX to { IndexFragment() },
        COMMUNITY to { CommunityFragment() },
        PUBLISH to { PublishFragment() },
        NOTIFICATION to { NotificationFragment() },
        MY to { MyFragment() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        (applicationContext as EyepetizerApplication).getAppViewModelProvider(this)
            .get(BaseViewModel::class.java).statusBarColor.observe(this, Observer {
            StatusBarUtil.setColor(this, it)
        })

        bottomNav.itemIconTintList = null
        bottomNav.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        //禁止滑动
        mainViewPager2.isUserInputEnabled = false

        //设置缓存页数
        mainViewPager2.offscreenPageLimit = fragmentCreator.size

        mainViewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragmentCreator.size
            }
            override fun createFragment(position: Int): Fragment {
                return fragmentCreator[position]?.invoke() ?: throw  ArrayIndexOutOfBoundsException(
                    "你的fragment越界了"
                )
            }
        }

        mainViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNav.menu.getItem(position).isChecked = true
            }
        })

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    mainViewPager2.setCurrentItem(INDEX, true)
                }
                R.id.navigation_community -> {
                    mainViewPager2.setCurrentItem(COMMUNITY, true)
                }
                R.id.navigation_publish -> {
                    mainViewPager2.setCurrentItem(PUBLISH, true)
                }
                R.id.navigation_notifications -> {
                    mainViewPager2.setCurrentItem(NOTIFICATION, true)
                }
                R.id.navigation_my -> {
                    mainViewPager2.setCurrentItem(MY, true)
                }
            }
            true
        }

        // val navController = findNavController(R.id.nav_host_fragment)
        //  navView.setupWithNavController(navController)
    }
}
