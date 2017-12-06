package com.arise.common.sdk.http

import android.os.Handler
import android.os.Looper
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
        private lateinit var handler: Handler
    }

    init {
        instance = this
        handler = Handler(Looper.getMainLooper())
    }


    fun doGet(url: String, realCallback: DataCallBack<String>) {
        val request = Request.Builder().url(url).build()

        execute(request, object : RawCallback {
            override fun onSuccess(result: Response) {
                if (result.body()?.string().isNullOrEmpty()) {
                    handler.post {
                        realCallback.onFail(BusinessException("body empty!", BusinessException.CODE_BODY_EMPTY))
                    }
                } else {
                    handler.post { realCallback.onSuccess(result.body()?.string() ?: "") }

                }
            }

            override fun onError(exception: BusinessException) {
                handler.post { realCallback.onFail(exception) }

            }

        })
    }

    fun doGet(url: String, path: String, realCallback: DataCallBack<File>) {
        val request = Request.Builder().url(url).build()
        execute(request, object : RawCallback {
            override fun onSuccess(result: Response) {
                if (result.body()?.byteStream() == null) {
                    handler.post { realCallback.onFail(BusinessException("body empty!", BusinessException.CODE_BODY_EMPTY)) }

                } else {
                    val inStream = result.body()!!.byteStream()
                    val file = FileUtil.writeFileFromStream(inStream, path)
                    handler.post { realCallback.onSuccess(file) }


                }
            }

            override fun onError(exception: BusinessException) {
                handler.post { realCallback.onFail(exception) }
            }

        })

    }

    fun doPost(url: String, realCallback: DataCallBack<File>, path: String) {


    }

    fun doPost(url:String,params:Map<String,String>,realCallback: DataCallBack<String>){
        val builder = FormBody.Builder()
        for ((key,value) in params){
            builder.add(key,value)
        }
        val body = builder.build()
        val request = Request.Builder().url(url).post(body).build()

        execute(request,object :RawCallback{
            override fun onSuccess(result: Response) {
                val message = result.body()?.string()
                if (message.isNullOrEmpty()) {
                    handler.post {
                        realCallback.onFail(BusinessException("body empty!", BusinessException.CODE_BODY_EMPTY))
                    }
                } else {
                    handler.post { realCallback.onSuccess(message?:" ") }

                }
            }

            override fun onError(exception: BusinessException) {
                handler.post { realCallback.onFail(exception) }

            }

        })

    }



    private fun execute(request: Request, callback: RawCallback) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (call.isCanceled) {
                    callback.onError(BusinessException("被取消了", BusinessException.CODE_CANCEL))
                } else {
                    callback.onError(BusinessException(e.message ?: BusinessException.MESSAGE_NO_NET_ERROR, BusinessException.CODE_NO_NET_ERROR))
                }
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