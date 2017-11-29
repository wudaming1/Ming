package com.arise.common.sdk.utils

import android.content.Context
import android.net.ConnectivityManager
import com.arise.common.sdk.BaseApplication

/**
 * Created by wudaming on 2017/11/16.
 */
object NetUtil {

    fun hasNetwork(): Boolean {
        val connectivityManager = BaseApplication.Companion.baseInstance.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isAvailable ?: false
    }
}