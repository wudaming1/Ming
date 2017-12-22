package com.arise.common.ming.user

import com.arise.common.ming.config.HttpConfig
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.http.Method
import com.arise.common.ming.http.MyHttpRequest
import com.arise.common.ming.http.callback.ActionCallback

/**
 * 工作：
 * 1. 指定http交互动词
 * 2. 使用带默认参数的方法，实现方法重载，方便业务调用。定制组装map的过程。
 *
 */
object UserService {

    private val userInfoUrl = HttpConfig.base_url + "/auth/userInfo"

    fun modifyUserInfo() {

    }

    fun getUserInfo(callback: ActionCallback<UserInfoBean>) {
        MyHttpRequest<UserInfoBean>(userInfoUrl, callback, Method.GET)
                .setclazz(UserInfoBean::class.java)
                .execute()
    }


    fun login(name: String, password: String, callback: ActionCallback<String>) {
        val loginUrl = HttpConfig.base_url + "/login"
        val map = HashMap<String, String>()
        map.put("name", name)
        map.put("password", password)
        MyHttpRequest<String>(loginUrl, map, callback, Method.POST)
                .setclazz(String::class.java)
                .execute()
    }

    fun register(name: String, password: String, callback: ActionCallback<String>) {
        val registerUrl = HttpConfig.base_url + "/register"
        val map = HashMap<String, String>()
        map.put("name", name)
        map.put("password", password)
        MyHttpRequest<String>(registerUrl, map, callback, Method.POST)
                .setclazz(String::class.java)
                .execute()
    }

}