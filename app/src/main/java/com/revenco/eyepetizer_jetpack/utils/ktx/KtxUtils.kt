package com.revenco.eyepetizer_jetpack.utils.ktx

import android.view.Gravity
import com.google.android.material.tabs.TabLayout


fun TabLayout.invokeTabLocation() {
    this.tabMode = TabLayout.MODE_SCROLLABLE
    //根据反射来改变
    val tabClz = TabLayout::class.java
    val indicatorField = tabClz.getDeclaredField("slidingTabIndicator")
    indicatorField.isAccessible = true
    val slidingTabIndicator = indicatorField.get(this)
    val setGravity =
        slidingTabIndicator!!::class.java.superclass!!.getDeclaredMethod(
            "setGravity",
            Int::class.java
        )
    setGravity.isAccessible = true
    setGravity.invoke(slidingTabIndicator, Gravity.CENTER)
}