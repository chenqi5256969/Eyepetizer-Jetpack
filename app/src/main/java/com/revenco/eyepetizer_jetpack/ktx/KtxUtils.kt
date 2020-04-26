package com.revenco.eyepetizer_jetpack.ktx

import android.view.Gravity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.revenco.eyepetizer_jetpack.R


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

fun ImageView.loadUrl(
    url: String? = "",
    isCrop: Boolean
) {
    if (isCrop) {
        Glide.with(context).asBitmap().load(url).apply {
            apply(RequestOptions.bitmapTransform(CircleCrop()))

        }.into(this)

    } else {
        Glide.with(context).load(url).apply {
            placeholder(R.drawable.wallpaper_8)
        }.into(this)

    }

}


