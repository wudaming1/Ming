package com.arise.common.sdk.http

import com.arise.common.sdk.http.callback.DataCallBack
import com.arise.common.sdk.http.callback.BusinessException
import com.arise.common.sdk.http.callback.RawCallback
import com.arise.common.sdk.utils.FileUtil
import okhttp3.*
import java.io.File
import java.io.IOException
import kotlin.properties.Delegates

/**
 * 管理网络请求库，封装隔离
 */
class HttpManager(private val okHttpClient: OkHttpClient) {

    companion object {
        var instance: HttpManager by Delegates.notNull()
    }

    init {
        instance = this
    }


    fun doGet(url: String, realCallback: DataCallBack<String>) {
        val request = Request.Builder().url(url).build()

        execute(request, object : RawCallback {
            override fun onSuccess(result: Response) {
                if (result.body()?.string().isNullOrEmpty()) {
                    realCallback.onFail(BusinessException("body empty!", BusinessException.CODE_BODY_EMPTY))
                } else {
                    realCallback.onSuccess(result.body()?.string() ?: "")
                }
            }

            override fun onError(exception: BusinessException) {
                realCallback.onFail(exception)
            }

        })
    }

    fun doGet(url: String, path: String, realCallback: DataCallBack<File>) {
        val request = Request.Builder().url(url).build()
        execute(request, object : RawCallback {
            override fun onSuccess(result: Response) {
                if (result.body()?.byteStream() == null) {
                    realCallback.onFail(BusinessException("body empty!", BusinessException.CODE_BODY_EMPTY))
                } else {
                    val inStream = result.body()!!.byteStream()
                    val file = FileUtil.writeFileFromStream(inStream, path)
                    realCallback.onSuccess(file)

                }
            }

            override fun onError(exception: BusinessException) {
                realCallback.onFail(exception)
            }

        })

    }

    fun doPost(url: String, realCallback: DataCallBack<File>, path: String) {

    }

    private fun execute(request: Request, callback: RawCallback) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(BusinessException(BusinessException.MESSAGE_NO_NET_ERROR, BusinessException.CODE_NO_NET_ERROR))
            }

            override fun onResponse(call: Call, response: Response) {
                if (call.isCanceled) {
                    callback.onError(BusinessException("被取消了", BusinessException.CODE_CANCEL))
                    return
                }
                if (response.isSuccessful) {
                    callback.onSuccess(response)
                } else {
                    callback.onError(BusinessException(response.message(), response.code()))
                }
            }

        })
    }


}