package com.arise.common.ming.user

import com.arise.common.ming.config.HttpConfig
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.http.MyHttpRequest
import com.arise.common.ming.http.callback.ActionCallback
import com.arise.common.sdk.http.Method
import com.arise.common.sdk.utils.JsonUtil
import java.io.File

/**
 * 工作：
 * 1. 指定http交互动词
 * 2. 使用带默认参数的方法，实现方法重载，方便业务调用。定制组装map的过程。
 *
 */
object UserService {

    private val userInfoUrl = HttpConfig.base_url + "/auth/userInfo"

    fun modifyUserInfo(userName: String = UserConfig.user?.userName ?: ""
                       , imgUrl: String = UserConfig.user?.imgUrl ?: ""
                       , sex: String = UserConfig.user?.sex ?: ""
                       , birthday: Long = UserConfig.user?.birthday ?: System.currentTimeMillis()
                       , callback: ActionCallback<String>) {
        val userInfo = UserInfoBean(userName,imgUrl,sex, birthday)
        MyHttpRequest.Builder<String>()
                .url(userInfoUrl)
                .clazz(String::class.java)
                .callback(callback)
                .method(Method.PUT)
                .params("userInfo",JsonUtil.writeValueAsString(userInfo))
                .build()
                .execute()
    }

    fun modifyPassword(password: String, callback: ActionCallback<String>){
        MyHttpRequest.Builder<String>()
                .url(userInfoUrl)
                .clazz(String::class.java)
                .callback(callback)
                .method(Method.PATCH)
                .params("password",password)
                .build()
                .execute()
    }

    fun modifyPortrait(file: File,callback: ActionCallback<String>){
        MyHttpRequest.Builder<String>()
                .url(userInfoUrl)
                .clazz(String::class.java)
                .callback(callback)
                .method(Method.PUT)
                .uploadFile(file)
                .build()
                .execute()
    }

    fun getUserInfo(callback: ActionCallback<UserInfoBean>) {
        MyHttpRequest.Builder<UserInfoBean>()
                .url(userInfoUrl)
                .clazz(UserInfoBean::class.java)
                .callback(callback)
                .method(Method.GET)
                .build()
                .execute()
    }


    fun login(name: String, password: String, callback: ActionCallback<String>) {
        val map = HashMap<String, String>()
        map.put("name", name)
        map.put("password", password)
        MyHttpRequest.Builder<String>()
                .url(HttpConfig.base_url + "/login")
                .clazz(String::class.java)
                .callback(callback)
                .method(Method.POST)
                .params(map)
                .build()
                .execute()

    }

    fun register(name: String, password: String, callback: ActionCallback<String>) {
        val map = HashMap<String, String>()
        map.put("name", name)
        map.put("password", password)
        MyHttpRequest.Builder<String>()
                .url(HttpConfig.base_url + "/register")
                .clazz(String::class.java)
                .callback(callback)
                .method(Method.POST)
                .params(map)
                .build()
                .execute()
    }

}