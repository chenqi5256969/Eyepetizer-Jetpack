import com.revenco.eyepetizer_jetpack.EyepetizerApplication
import com.revenco.eyepetizer_jetpack.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

class EyepetizerCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (NetworkUtils.isNetConneted(EyepetizerApplication.context)) {
            var response = chain.proceed(request)
            val maxAge = 60
            val cacheControl = response.header("Cache-Control")
            return if (cacheControl == null
                || cacheControl.contains("no-store")
                || cacheControl.contains("no-cache")
                || cacheControl.contains("must-revalidate")
                || cacheControl.contains("max-age=0")
            ) {
                response = response.newBuilder().removeHeader("Pragma").header(
                    "Cache-Control",
                    "public, max-age=$maxAge"
                ).build()
                response
            } else {
                response
            }
        } else {
            request =
                request.newBuilder().removeHeader("Pragma")
                    .header(
                        "Cache-Control",
                        "public, only-if-cached"
                    ).build()
            return chain.proceed(request)
        }
    }

}

