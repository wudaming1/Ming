package com.arise.common.ming.base

import android.content.Context
import com.arise.common.ming.MyApplication
import com.arise.common.ming.user.UserInfoBean
import com.arise.common.sdk.BaseApplication
import com.arise.common.sdk.db.PreferenceManagerHelp

/**
 * SharePreference属于轻量级的键值存储方式,存储基础数据类型数据。
 */
object PreferenceManager {

    private val preferenceHelp = PreferenceManagerHelp(MyApplication.instance.getSharedPreferences("user", Context.MODE_PRIVATE))


    fun saveDebugHttpUrl(url: String) {
        preferenceHelp.putString("url", url)
    }

    fun getDebugHttpUrl(): String {
        return preferenceHelp.getString("url", "http://172.28.19.179:8080/Servlet")
    }


    fun saveToken(token: String) {
        preferenceHelp.putString("token", token)
    }

    fun getToken(): String {
        return preferenceHelp.getString("token")
    }

    fun saveLoginState(isLogin: Boolean) {
        preferenceHelp.putBoolean("login_state", isLogin)
    }

    fun isLogin() = preferenceHelp.getBoolean("login_state")


    fun saveUserInfo(infoBean: UserInfoBean?) {
        preferenceHelp.putObject("user_info", infoBean)
    }

    fun deleteUserInfo() {
        preferenceHelp.putObject("user_info", null)
    }

    fun getUserInfo(): UserInfoBean? {
        return preferenceHelp.getObject("user_info", UserInfoBean::class.java)
    }


    fun saveUserName(name: String) {
        preferenceHelp.putString("user_name", name)
    }

    fun getUserName() = { preferenceHelp.getString("user_name") }


    fun savePassword(password: String) {
        preferenceHelp.putString("password", password)
    }

    fun getPassword() = { preferenceHelp.getString("password") }
}