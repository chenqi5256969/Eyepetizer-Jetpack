package com.revenco.eyepetizer_jetpack.utils.ktx

import android.view.Gravity
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.utils.GlideApp


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
    isCrop: Boolean? = false
) {
    GlideApp.with(context).load(url).apply {
        if (isCrop!!) {
            apply(RequestOptions.bitmapTransform(CircleCrop()))
        }
        transition(DrawableTransitionOptions.withCrossFade())
        placeholder(R.drawable.wallpaper_0)
//        into(DrawableImageViewTarget(this@loadUrl))
        into(this@loadUrl)
    }

}


