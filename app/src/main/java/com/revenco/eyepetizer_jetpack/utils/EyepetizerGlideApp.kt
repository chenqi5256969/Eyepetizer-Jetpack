package com.revenco.eyepetizer_jetpack.utils

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class EyepetizerGlideApp : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //配置内存缓存大小
        val calculator = MemorySizeCalculator.Builder(context)
            .setMemoryCacheScreens(2f)
            .build()
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))

        //磁盘缓存配置
        builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context))

    }
}