package com.arise.common.ming.config

import com.arise.common.ming.BuildConfig

/**
 * 全局配置信息
 */
class Config {

}

object HttpConfig {
    var base_url = ""

    init {
        base_url = if (BuildConfig.DEBUG) {
            "http://172.28.17.29:8080/Servlet"
        } else {
            "http://172.28.17.176:8080/Servlet"
        }
    }
}