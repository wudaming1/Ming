package com.arise.common.ming.http

import com.arise.common.ming.http.callback.ActionCallback
import com.arise.common.sdk.http.HttpManager
import com.arise.common.sdk.http.callback.BusinessException
import com.arise.common.sdk.http.callback.DataCallBack

/**
 * Created by wudaming on 2017/11/21.
 */
class MyHttpRequest(private val url: String, private val method: Method = Method.GET) {


    private lateinit var path: String
    private val actionCallback: ActionCallback<Any>? = null


    constructor(url: String, path: String, method: Method = Method.GET) : this(url, method) {
        this.path = path
    }


    fun execute() {
        when (method) {
            Method.GET -> {
                HttpManager.instance.doGet(url, object : DataCallBack<String> {
                    override fun onFail(exception: BusinessException) {
                        actionCallback?.onError(exception)
                    }

                    override fun onSuccess(result: String) {

                        actionCallback?.onSuccess(result)
                    }

                })
            }
            Method.POST -> ""
        }
    }


}


enum class Method {
    GET, POST
}