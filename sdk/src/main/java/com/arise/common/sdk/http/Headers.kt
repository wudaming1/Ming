package com.arise.common.sdk.http

import com.arise.common.sdk.utils.DeviceUtil
import okhttp3.Request

/**
 * Created by wudaming on 2017/11/16.
 */
object Headers {
    var header = mutableMapOf<String, String>()

    fun init() {
        header.clear()
        try {
            header.put("mac", DeviceUtil.getMac())
            header.put("imei", DeviceUtil.getIMSI())
            header.put("deviceId", DeviceUtil.generateDeviceId())
        } catch (e: Exception) {
        }
    }
}