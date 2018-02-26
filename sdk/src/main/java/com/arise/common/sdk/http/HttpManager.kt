package com.arise.common.sdk.http

import android.os.Handler
import android.os.Looper
import com.arise.common.sdk.http.Method.*
import com.arise.common.sdk.http.callback.*
import com.arise.common.sdk.utils.FileUtil
import com.arise.common.sdk.utils.ToastUtil
import okhttp3.*
import java.io.File
import java.io.IOException
import java.io.InputStream
import kotlin.properties.Delegates

/**
 * 管理网络请求库，封装隔离
 * request由我们主动构造，生成的内容确定。
 * 但是Response的内容并不确定，即使与服务端约定好，由于网络环境等因素，并不一定返回我们约定的内容，
 * 所以Response内容依赖content-type解析
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

    fun commitRequest(url: String, method: Method, type: Type, file: File?, params: Map<String, String>, realCallback: DataCallBack) {
        val builder = Request.Builder()
        builder.url(url)

        when (method) {
            GET -> builder.get()
            POST -> {
                builder.post(createBody(type, file, params))
            }
            PUT -> {
                builder.put(createBody(type, file, params))
            }
            PATCH -> {
                builder.patch(createBody(type, file, params))
            }
            DELETE -> {ToastUtil.showToast("待扩展DELETE")}
            HEAD -> {ToastUtil.showToast("待扩展HEAD")}
            OPTIONS -> {ToastUtil.showToast("待扩展OPTIONS")}
        }

        val request = builder.build()

        execute(request, realCallback)
    }

    private fun createBody(type: Type, file: File?, params: Map<String, String>): RequestBody {
        //todo 这个地方问题很大。。。
        return if (type == Type.File) {
            val build = MultipartBody.Builder()
            build.setType(MultipartBody.FORM)
            build.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file!!.name + "\""),
                    createFilePartBody(file))
            build.build()
        } else {
            val bodyBuilder = FormBody.Builder()
            for ((key, value) in params) {
                bodyBuilder.add(key, value)
            }
            bodyBuilder.build()
        }
    }

    private fun createFilePartBody(file: File): RequestBody{
        val extend = file.name.split(".").last()
        var mediaType = "image/jpg"
        when(extend){
            "jpg" -> mediaType = "image/jpg"
            "png" -> mediaType = "image/png"
        }
        return RequestBody.create(MediaType.parse(mediaType), file)
    }


//    fun doGet(url: String, realCallback: DataCallBack) {
//        val request = Request.Builder().url(url).build()
//        execute(request, StringCallBack(realCallback))
//    }
//
//    /**
//     * post单文件上传
//     */
//    fun doPost(url: String, file: File, realCallback: DataCallBack) {
//        val fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
////        val requestBody = MultipartBody.Builder()
////                .setType(MultipartBody.FORM)
////                .addPart()
//        val request = Request.Builder()
//                .url(url)
//                .post(fileBody)
//                .build()
//        execute(request, realCallback)
//    }
//
//    /**
//     * post普通参数请求服务器
//     */
//    fun doPost(url: String, params: Map<String, String>, realCallback: DataCallBack) {
//        val builder = FormBody.Builder()
//        for ((key, value) in params) {
//            builder.add(key, value)
//        }
//        val body = builder.build()
//        val request = Request.Builder()
//                .url(url)
//                .post(body)
//                .build()
//
//        execute(request, realCallback)
//
//    }
//
//    fun doPut(url: String, file: File, realCallback: DataCallBack) {
//        val fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
//        val request = Request.Builder()
//                .url(url)
//                .put(fileBody)
//                .build()
//        execute(request, StringCallBack(realCallback))
//    }
//
//    /**
//     * put普通参数请求服务器
//     */
//    fun doPut(url: String, params: Map<String, String>, realCallback: DataCallBack) {
//        val builder = FormBody.Builder()
//        for ((key, value) in params) {
//            builder.add(key, value)
//        }
//        val body = builder.build()
//        val request = Request.Builder()
//                .url(url)
//                .put(body)
//                .build()
//
//        execute(request, StringCallBack(realCallback))
//
//    }

    private fun execute(request: Request, callback: DataCallBack) {
        execute(request, StringCallBack(callback))
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

enum class Method {
    GET//从服务器取出资源（一项或多项）
    ,
    POST//在服务器新建一个资源。
    ,
    PUT//在服务器更新资源（客户端提供改变后的完整资源）。
    ,
    PATCH//在服务器更新资源（客户端提供改变的属性）。
    ,
    DELETE//从服务器删除资源。
    ,
    HEAD//获取资源的元数据。
    ,
    OPTIONS//获取信息，关于资源的哪些属性是客户端可以改变的。
}


enum class Type {
    String,
    File
}