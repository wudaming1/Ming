package com.arise.common.ming.config

import com.arise.common.ming.BuildConfig
import com.arise.common.ming.user.UserBean

/**
 * 全局配置信息
 */
class Config {

}

object HttpConfig {
    var base_url = ""

    init {
        base_url = if (BuildConfig.DEBUG) {
            "http://172.28.19.179:8080/Servlet"
        } else {
            "http://172.28.17.176:8080/Servlet"
        }
    }
}

object UserConfig {
    var islogin = false
    var user: UserBean? = null

}