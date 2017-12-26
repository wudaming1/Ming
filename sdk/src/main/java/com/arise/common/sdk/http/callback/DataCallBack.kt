package com.arise.common.sdk.http.callback

import com.arise.common.sdk.BaseApplication

/**
 * Response已经被解析为T类型的Data
 * 放弃T泛型，改传一个Any对象，在java运行时，并没有泛型信息，即DataCallBack<String>,DataCallBack<Int>
 * 具有相同的签名DataCallBack，重载方法会被认为是同一个。
 * 定义Any的话,后面自己去强制转型。
 *
 * 回调中完全去掉OkHttp的信息。
 *
 */
abstract class DataCallBack{
    abstract fun onFail(exception: BusinessException)

    abstract fun onSuccess(resp: Any)

    fun postOnUIThread(r:()->Unit) {
        BaseApplication.mainHandler.post(r)
    }

}