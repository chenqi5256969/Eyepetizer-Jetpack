package com.revenco.eyepetizer_jetpack.config

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class EyepetizerGlideApp : AppGlideModule() {
    /*override fun applyOptions(context: Context, builder: GlideBuilder) {

        val calculator = MemorySizeCalculator.Builder(context)
            .setMemoryCacheScreens(4f)
            .setBitmapPoolScreens(5f)
            .build()
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))

        builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context))
            .setBitmapPool(LruBitmapPool(calculator.memoryCacheSize.toLong()))
    }*/
}