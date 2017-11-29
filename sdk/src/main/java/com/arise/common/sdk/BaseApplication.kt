package com.arise.common.sdk

import android.app.Application
import com.arise.common.sdk.http.WriteCacheControllInterceptor
import com.arise.common.sdk.utils.CACHE_PATH
import com.arise.common.sdk.utils.FileUtil
import com.arise.common.sdk.utils.NET_CACHE_PATH
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * Created by wudaming on 2017/11/16.
 */
open class BaseApplication : Application() {

    companion object {
        var baseInstance: BaseApplication by Delegates.notNull()
    }

    lateinit var httpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        baseInstance = this
        initHttpClient()

    }


    private fun initHttpClient() {

        val cacheFile = File(NET_CACHE_PATH)
        val cache = Cache(cacheFile, 10 * 1024 * 1024)

        httpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(WriteCacheControllInterceptor())
                .build()
    }
}