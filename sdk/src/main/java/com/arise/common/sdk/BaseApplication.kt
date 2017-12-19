package com.arise.common.sdk

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.arise.common.sdk.http.HeaderInterceptor
import com.arise.common.sdk.http.WriteCacheControllInterceptor
import com.arise.common.sdk.utils.FileUtil.NET_CACHE_PATH
import com.facebook.drawee.backends.pipeline.Fresco
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates
import com.orhanobut.logger.PrettyFormatStrategy
import com.orhanobut.logger.FormatStrategy


/**
 * Created by wudaming on 2017/11/16.
 */
open class BaseApplication : Application() {

    companion object {
        var baseInstance: BaseApplication by Delegates.notNull()
        val headers = hashMapOf<String, String>()
        val mainHander = Handler(Looper.getMainLooper())
    }

    lateinit var httpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        baseInstance = this
        initHttpClient()
        initLogger()

        Fresco.initialize(this)
    }

    /**
     * https://github.com/orhanobut/logger
     */
    private fun initLogger() {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Ming")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
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
                .addInterceptor(HeaderInterceptor())
                .build()
    }
}