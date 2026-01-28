package com.github.operador231.core.network.interceptor

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response

public class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithHeaders = request.newBuilder()
            .addHeader("User-Agent", "AniFlux (Android; API ${Build.VERSION.SDK_INT})")
            .header("Accept", "application/json")
            .header("Cache-Control", "no-cache")
            .build()

        return chain.proceed(requestWithHeaders)
    }
}