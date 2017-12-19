package com.arise.common.ming

import android.os.Build
import android.os.Handler
import android.os.Looper
import com.arise.common.ming.base.PreferenceManager
import com.arise.common.sdk.BaseApplication
import com.arise.common.sdk.http.HttpManager
import kotlin.properties.Delegates

/**
 * Created by wudaming on 2017/11/17.
 */
class MyApplication:BaseApplication(){

    companion object {
        var instance:MyApplication by Delegates.notNull()
        var httpManager : HttpManager by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        httpManager = HttpManager(httpClient)

        initHeader()
    }

    private fun initHeader() {
        headers.put("token",PreferenceManager.getToken())
        headers.put("os","android")
        headers.put("version_name",BuildConfig.VERSION_NAME)
        headers.put("version_code",BuildConfig.VERSION_CODE.toString())
        headers.put("brand",Build.BRAND)
    }
}