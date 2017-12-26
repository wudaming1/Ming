package com.arise.common.ming.http

import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.http.callback.ActionCallback
import com.arise.common.ming.user.login.LoginActivity
import com.arise.common.sdk.http.HttpManager
import com.arise.common.sdk.http.Method
import com.arise.common.sdk.http.Type
import com.arise.common.sdk.http.callback.BusinessException
import com.arise.common.sdk.http.callback.DataCallBack
import com.arise.common.sdk.utils.FileUtil
import com.arise.common.sdk.utils.JsonUtil
import com.arise.common.sdk.utils.ToastUtil
import java.io.File
import java.io.InputStream

/**
 * 负责解析自定义的一些状态，每个请求对应一个对象。
 * Builder 模式很好用，可以避免臃肿的构造函数参数列表，还能方便地复用预先定义好的配置对象的代码。 Kotlin 的 apply 扩展原生支持 Builder 模式
 */
class MyHttpRequest<T : Any> private constructor() {

    private lateinit var clazz: Class<T>
    private lateinit var url: String
    private lateinit var method: Method

    private lateinit var path: String
    private var file: File? = null
    private var actionCallback: ActionCallback<T>? = null
    private val params = hashMapOf<String, String>()

    private var requestContentType = Type.String


    fun execute() {
        HttpManager.instance.commitRequest(url, method, requestContentType, file, params, StringCallBack())
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
                if (clazz == String::class.java) {
                    actionCallback?.onSuccess(resultBean.data as T)
                } else {
                    val parserResult = JsonUtil.readValue<T>(resultBean.data, clazz)
                    actionCallback?.onSuccess(parserResult)
                }

            }

        }

    }

    inner class StringCallBack : DataCallBack() {

        override fun onFail(exception: BusinessException) {
            postOnUIThread { actionCallback?.onError(exception) }

        }

        override fun onSuccess(resp: Any) {
            if (resp is String) {
                val resultBean = JsonUtil.readValue<HttpResultBean>(resp, HttpResultBean::class.java)
                if (resultBean != null) {
                    postOnUIThread { parserResp(resultBean) }

                } else {
                    postOnUIThread {
                        actionCallback?.onError(BusinessException(HttpResultBean::class.java.simpleName + "解析错误，" + resp, BusinessException.JSON_PARSE_ERROR))
                    }
                }
            } else if (resp is InputStream) {
                val file = FileUtil.writeFileFromStream(resp, path)
                postOnUIThread { actionCallback?.onSuccess(file.absoluteFile as T) }

            } else {
                postOnUIThread {
                    actionCallback?.onError(BusinessException(HttpResultBean::class.java.simpleName + "解析错误，" + resp, BusinessException.JSON_PARSE_ERROR))
                }
            }
        }

    }


    class Builder<T : Any> {
        private val request = MyHttpRequest<T>()

        fun method(method: Method): Builder<T> {
            request.method = method
            return this
        }

        fun clazz(clazz: Class<T>): Builder<T> {
            request.clazz = clazz
            return this
        }

        fun url(url: String): Builder<T> {
            request.url = url
            return this
        }

        fun callback(callback: ActionCallback<T>): Builder<T> {
            request.actionCallback = callback
            return this
        }

        fun params(map: HashMap<String, String>): Builder<T> {
            request.params.putAll(map)
            return this
        }

        fun params(key: String, value: String): Builder<T> {
            request.params.put(key, value)
            return this
        }

        fun downloadPath(path: String): Builder<T> {
            request.path = path
            return this
        }

        fun uploadFile(file: File): Builder<T> {
            request.file = file
            request.requestContentType = Type.File
            return this
        }

        fun build(): MyHttpRequest<T> {
            return request
        }

    }

}

