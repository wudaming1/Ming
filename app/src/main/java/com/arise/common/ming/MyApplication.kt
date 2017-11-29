package com.arise.common.ming

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
    }
}