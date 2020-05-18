package com.revenco.eyepetizer_jetpack.net

import com.revenco.eyepetizer_jetpack.BuildConfig
import com.revenco.eyepetizer_jetpack.EyepetizerApplication
import com.revenco.eyepetizer_jetpack.net.constant.*
import com.revenco.eyepetizer_jetpack.utils.NetworkUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
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
            return OkHttpClient().newBuilder()
                .cache(cache)
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .addInterceptor(hir)
                .connectTimeout(Net_ConnectTime_Out, TimeUnit.MILLISECONDS)
                .writeTimeout(Write_ConnectTime_Out, TimeUnit.MILLISECONDS)
                .readTimeout(Read_ConnectTime_Out, TimeUnit.MILLISECONDS)
                .build()
        }

    private val REWRITE_RESPONSE_INTERCEPTOR = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val originalResponse = chain.proceed(chain.request())
            val cacheControl = originalResponse.header("Cache-Control")
            return if (cacheControl == null ||
                cacheControl.contains("no-store") ||
                cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") ||
                cacheControl.contains("max-age=0")
            ) {
                originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 5000)
                    .build()
            } else {
                originalResponse
            }
        }
    }

    private val REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            if (!NetworkUtils.isNetConneted(EyepetizerApplication.context)) {
                request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached")
                    .build()
            }
            return chain.proceed(request)
        }
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