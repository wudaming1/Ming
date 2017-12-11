package com.arise.common.ming.base

import android.content.Context
import com.arise.common.ming.MyApplication
import com.arise.common.sdk.BaseApplication
import com.arise.common.sdk.db.PreferenceManagerHelp

/**
 * SharePreference属于轻量级的键值存储方式,存储基础数据类型数据。
 */
object PreferenceManager {

    private val preferenceHelp = PreferenceManagerHelp(MyApplication.instance.getSharedPreferences("user", Context.MODE_PRIVATE))


    fun saveToken(token: String) {
        preferenceHelp.putString("token", token)
    }

    fun getToken():String {
        return preferenceHelp.getString("token") }


    fun saveUserName(name: String) {
        preferenceHelp.putString("user_name", name)
    }

    fun getUserName() = { preferenceHelp.getString("user_name") }


    fun savePassword(password: String) {
        preferenceHelp.putString("password", password)
    }

    fun getPassword() = { preferenceHelp.getString("password") }
}