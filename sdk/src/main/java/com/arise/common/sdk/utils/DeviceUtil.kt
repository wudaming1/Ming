package com.arise.common.sdk.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.arise.common.sdk.BaseApplication
import com.arise.common.sdk.permission.Permissions
import java.io.FileReader
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.io.Reader

/**
 * Created by wudaming on 2017/11/16.
 */
object DeviceUtil{


    /**
     * 获取手机IMSI
     */
    fun getIMSI(): String {
        try {
            val telephonyManager = BaseApplication.Companion.baseInstance.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var imsi: String? = ""
            //获取IMSI号
            if (ContextCompat.checkSelfPermission(BaseApplication.Companion.baseInstance, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                 imsi = telephonyManager.subscriberId
                if (null == imsi) {
                    imsi = ""
                }

            }
            return imsi?:""
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }


    fun generateDeviceId(): String {
        val tm = BaseApplication.Companion.baseInstance.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ContextCompat.checkSelfPermission(BaseApplication.Companion.baseInstance, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val DEVICE_ID = tm.deviceId
            if (!TextUtils.isEmpty(DEVICE_ID))
                return DEVICE_ID
        }
        return ""
    }


    fun getMac():String{
        var mac = ""
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

                val wifi = BaseApplication.Companion.baseInstance.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val info = wifi.connectionInfo
                if (info != null && !TextUtils.isEmpty(info.macAddress)) {
                    mac = info.macAddress
                }
            } else {
                var str: String? = ""
                try {
                    val pp = Runtime.getRuntime().exec(
                            "cat /sys/class/net/wlan0/address ")
                    val ir = InputStreamReader(pp.inputStream)
                    val input = LineNumberReader(ir)

                    while (null != str) {
                        str = input.readLine()
                        if (str != null) {
                            mac = str.trim { it <= ' ' }// 去空格
                            break
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

                if (TextUtils.isEmpty(mac)) {
                    try {
                        mac = loadFileAsString("/sys/class/net/eth0/address")
                                .toUpperCase().substring(0, 17)
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }

                }
            }
        } catch (e: Exception) {
        }

        return mac

    }


    @Throws(Exception::class)
    private fun loadFileAsString(fileName: String): String {
        val reader = FileReader(fileName)
        val text = loadReaderAsString(reader)
        reader.close()
        return text
    }


    @Throws(Exception::class)
    private fun loadReaderAsString(reader: Reader): String {
        val builder = StringBuilder()
        val buffer = CharArray(4096)
        var readLength = reader.read(buffer)
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength)
            readLength = reader.read(buffer)
        }
        return builder.toString()
    }
}