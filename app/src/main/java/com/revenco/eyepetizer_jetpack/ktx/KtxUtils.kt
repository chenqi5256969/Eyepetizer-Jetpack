package com.revenco.eyepetizer_jetpack.ktx

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.tabs.TabLayout
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.config.GlideApp


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
    thumbnailUrl: String? = "",
    isCrop: Boolean
) {
    if (isCrop) {
        val target = object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                this@loadUrl.setImageDrawable(placeholder)
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                this@loadUrl.setImageBitmap(resource)
            }
        }
        Glide.with(context).asBitmap().load(url).apply {
            apply(RequestOptions.bitmapTransform(CircleCrop()))

        }.into(target)

    } else {
        val target = object : CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                this@loadUrl.setImageDrawable(placeholder)
            }

            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                this@loadUrl.setImageDrawable(resource)
            }
        }
        Glide.with(context).load(url).apply {
            transition(
                DrawableTransitionOptions.withCrossFade(
                    DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(
                        true
                    ).build()
                )
            )
            thumbnail(GlideApp.with(context).load(thumbnailUrl))
            placeholder(R.drawable.wallpaper_8)
        }.into(target)

    }

}


