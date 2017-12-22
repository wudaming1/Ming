package com.arise.common.ming.config

import com.arise.common.ming.BuildConfig
import com.arise.common.ming.MyApplication
import com.arise.common.ming.base.MessageEvent
import com.arise.common.ming.base.PreferenceManager
import com.arise.common.ming.user.UserInfoBean
import org.greenrobot.eventbus.EventBus

/**
 * 全局配置信息
 */
object Config {

    val BASE_PATH = ""
    val CACHE_PATH = BASE_PATH + "/cache"

}

object HttpConfig {
    var base_url = ""

    init {
        base_url = if (BuildConfig.DEBUG) {
            PreferenceManager.getDebugHttpUrl()
        } else {
            "http://172.28.17.176:8080/Servlet"
        }
    }
}

object UserConfig {
    var isLogin = PreferenceManager.isLogin()
    var user: UserInfoBean? = PreferenceManager.getUserInfo()


    fun loginOut() {
        isLogin = false
        user = null
        PreferenceManager.saveLoginState(false)
        PreferenceManager.deleteUserInfo()
        MyApplication.instance.updateToken("")
        EventBus.getDefault().post(MessageEvent.UNLOGIN)

    }

    fun login(userInfoBean: UserInfoBean) {
        isLogin = true
        user = userInfoBean
        PreferenceManager.saveLoginState(true)
        PreferenceManager.saveUserInfo(userInfoBean)
        EventBus.getDefault().post(MessageEvent.LOGIN)
    }

}