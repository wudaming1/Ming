package com.arise.common.sdk.permission

import android.Manifest
import android.os.Build

/**
 * Created by wudaming on 2017/11/16.
 */
object Permissions{
    val CALENDAR: Array<String>   // 读写日历。
    val CAMERA: Array<String>     // 相机。
    val CONTACTS: Array<String>   // 读写联系人。
    val LOCATION: Array<String>   // 读位置信息。
    val MICROPHONE: Array<String> // 使用麦克风。
    val PHONE: Array<String>      // 读电话状态、打电话、读写电话记录。
    val SENSORS: Array<String>    // 传感器。
    val SMS: Array<String>        // 读写短信、收发短信。
    val STORAGE: Array<String>    // 读写存储卡。

    init {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            CALENDAR = arrayOf()
            CAMERA = arrayOf()
            CONTACTS = arrayOf()
            LOCATION = arrayOf()
            MICROPHONE = arrayOf()
            PHONE = arrayOf()
            SENSORS = arrayOf()
            SMS = arrayOf()
            STORAGE = arrayOf()
        } else {
            CALENDAR = arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)

            CAMERA = arrayOf(Manifest.permission.CAMERA)

            CONTACTS = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS)

            LOCATION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

            MICROPHONE = arrayOf(Manifest.permission.RECORD_AUDIO)

            PHONE = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS)

            SENSORS = arrayOf(Manifest.permission.BODY_SENSORS)

            SMS = arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS)

            STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}