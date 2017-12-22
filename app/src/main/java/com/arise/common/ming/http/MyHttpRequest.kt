package com.arise.common.ming.http

import com.arise.common.ming.base.PreferenceManager
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.http.callback.ActionCallback
import com.arise.common.ming.user.login.LoginActivity
import com.arise.common.sdk.http.HttpManager
import com.arise.common.sdk.http.callback.BusinessException
import com.arise.common.sdk.http.callback.DataCallBack
import com.arise.common.sdk.utils.JsonUtil
import com.arise.common.sdk.utils.ToastUtil

/**
 * 负责解析自定义的一些状态，每个请求对应一个对象。
 * Builder 模式很好用，可以避免臃肿的构造函数参数列表，还能方便地复用预先定义好的配置对象的代码。 Kotlin 的 apply 扩展原生支持 Builder 模式
 */
class MyHttpRequest<T : Any>(private val url: String, private val method: Method = Method.GET) {


    private lateinit var path: String
    private var actionCallback: ActionCallback<T>? = null
    private val map = hashMapOf<String, String>()
    private var clazz: Class<T>? = null


    constructor(url: String, path: String, method: Method = Method.GET) : this(url, method) {
        this.path = path
    }

    constructor(url: String, callback: ActionCallback<T>, method: Method = Method.GET) : this(url, method) {
        actionCallback = callback
    }

    constructor(url: String, map: HashMap<String, String>, callback: ActionCallback<T>, method: Method = Method.GET) : this(url, method) {
        actionCallback = callback
        this.map.putAll(map)
    }

    fun setclazz(clazz: Class<T>): MyHttpRequest<T> {
        this.clazz = clazz
        return this
    }


    fun execute() {
        when (method) {
            Method.GET -> {
                HttpManager.instance.doGet(url, StringCallBack())
            }
            Method.POST -> {
                HttpManager.instance.doPost(url, map, StringCallBack())
            }
        }
    }


    fun parserResp(resultBean: HttpResultBean) {
        when (resultBean.resultCode) {
            HttpResultCode.FAIL -> actionCallback?.onError(BusinessException(resultBean.message, resultBean.resultCode))
            HttpResultCode.DATABASE_ERR -> actionCallback?.onError(BusinessException(resultBean.message, resultBean.resultCode))
            HttpResultCode.PARAM_ERR -> actionCallback?.onError(BusinessException(resultBean.message, resultBean.resultCode))
            HttpResultCode.TOKEN_INVALID -> {
                ToastUtil.showToast("登录过期，请重新登录！")
                UserConfig.loginOut()
                LoginActivity.goLogin()
            }
            HttpResultCode.SUCCESS -> {
                if (clazz == null) {
                    throw Exception("未指定解析类型！")
                } else if(clazz == String::class.java){
                    actionCallback?.onSuccess(resultBean.data as T)
                }else{
                    val parserResult = JsonUtil.readValue<T>(resultBean.data, clazz!!)
                    actionCallback?.onSuccess(parserResult)
                }

            }

        }

    }

    inner class StringCallBack : DataCallBack<kotlin.String> {

        override fun onFail(exception: BusinessException) {
            actionCallback?.onError(exception)
        }

        override fun onSuccess(resp: String) {
            val resultBean = JsonUtil.readValue<HttpResultBean>(resp, HttpResultBean::class.java)
            if (resultBean != null) {
                parserResp(resultBean)
            } else {
                actionCallback?.onError(BusinessException(HttpResultBean::class.java.simpleName + "解析错误，" + resp, BusinessException.JSON_PARSE_ERROR))
            }
        }

    }


}


enum class Method {
    GET, POST, PUT
}