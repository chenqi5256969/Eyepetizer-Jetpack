import android.util.Log
import com.revenco.eyepetizer_jetpack.EyepetizerApplication
import com.revenco.eyepetizer_jetpack.utils.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class EyepetizerCacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        return if (NetworkUtils.isNetConneted(EyepetizerApplication.context)) {
            val response = chain.proceed(request)
            val maxAge = 60
            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            Log.e("CacheInterceptor", " no network load cahe")
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            val response = chain.proceed(request)
            val maxStale = 60 * 60 * 24 * 3
            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }
}

