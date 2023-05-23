package org.seonhwan.android.veloginmobile.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import org.seonhwan.android.veloginmobile.BuildConfig

class VelogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val newRequest = originRequest.newBuilder()
            .header(
                "X-AUTH-TOKEN",
                BuildConfig.VELOG_AUTH_TOKEN,
            ).build()
        return chain.proceed(newRequest)
    }
}
