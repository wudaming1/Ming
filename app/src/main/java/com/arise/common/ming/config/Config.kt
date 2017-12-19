package com.arise.common.ming.config

import com.arise.common.ming.BuildConfig
import com.arise.common.ming.base.PreferenceManager
import com.arise.common.ming.user.UserInfoBean

/**
 * 全局配置信息
 */
class Config {

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
    var islogin = false
    var user: UserInfoBean? = null


    fun loginOut(){
        islogin = false
        user = null
        PreferenceManager.saveToken("")
    }

}