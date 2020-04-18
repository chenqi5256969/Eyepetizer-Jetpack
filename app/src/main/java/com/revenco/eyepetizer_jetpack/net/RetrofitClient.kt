package com.revenco.eyepetizer_jetpack.net

import com.revenco.eyepetizer_jetpack.BuildConfig
import com.revenco.eyepetizer_jetpack.EyepetizerApplication
import com.revenco.eyepetizer_jetpack.net.constant.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class RetrofitClient private constructor() {


    private val cacheFile: File
        get() {
            return File(EyepetizerApplication.context.externalCacheDir, "cache")
        }

    private val cache: Cache
        get() {
            return Cache(cacheFile, MAX_CACHE_SIZE)
        }


    private val okHttpClient: OkHttpClient
        get() {
            val hir = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                hir.level = HttpLoggingInterceptor.Level.BODY
            } else {
                hir.level = HttpLoggingInterceptor.Level.BASIC
            }
            return OkHttpClient.Builder()
               // .cache(cache)
                .addInterceptor(hir)
               // .addInterceptor(EyepetizerCacheInterceptor())
                //.addNetworkInterceptor(EyepetizerCacheInterceptor())
                .connectTimeout(Net_ConnectTime_Out, TimeUnit.MILLISECONDS)
                .writeTimeout(Write_ConnectTime_Out, TimeUnit.MILLISECONDS)
                .readTimeout(Read_ConnectTime_Out, TimeUnit.MILLISECONDS)
                .build()
        }

    private val retrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    companion object {
        @Volatile
        private var instance: RetrofitClient? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: RetrofitClient().also {
                instance = it
            }
        }
    }

    fun createApi() =
        retrofit.create(EyepetizerAPI::class.java)
}