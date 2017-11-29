package com.arise.common.sdk.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by wudaming on 2017/11/16.
 */
class WriteCacheControllInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val cacheState = response.cacheControl().toString()
        return if (cacheState.isNotEmpty()) response else
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build()
    }

}