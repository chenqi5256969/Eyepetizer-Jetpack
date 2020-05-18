package com.revenco.eyepetizer_jetpack.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.config.GlideApp


class GlideUtils {
    companion object {
        private val options = RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.wallpaper_8)
            .error(R.drawable.wallpaper_0)
            .dontAnimate()

        fun loadImageNormal(context: Context, url: String, imageView: ImageView) {
            GlideApp.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
        }


        /**
         * 将图片自适应view
         */
        fun loadImageFitView(context: Context, url: String, imageView: ImageView) {
            GlideApp.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(object : CustomTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val resourceWidth = resource.width
                        val resourceHeight = resource.height

                        val imageViewWidth = imageView.width

                        var imageViewHeight =
                            ((imageViewWidth * 1f / resourceWidth * 1f) * resourceHeight).toInt()

                        val maxHeight = SizeUtils.px2dp(800f)

                        if (imageViewHeight > maxHeight) {
                            imageViewHeight = maxHeight
                        }

                        val layoutParams = imageView.layoutParams
                        layoutParams.height = imageViewHeight
                        imageView.layoutParams = layoutParams

                        GlideApp.with(context).load(url).apply(options).into(imageView)
                    }

                })
        }

        fun loadCircleImage(context: Context, url: String, imageView: ImageView) {
            GlideApp.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(imageView!!)
        }

        fun loadCornersImage(context: Context, url: String, imageView: ImageView) {
            GlideApp.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(imageView!!)
        }

        fun loadLocalImage(context: Context, url: Int, imageView: ImageView) {
            GlideApp.with(context)
                .load(url)
                .apply(options)
                .into(imageView!!)
        }
    }
}