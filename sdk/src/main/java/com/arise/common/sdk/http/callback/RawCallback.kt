package com.arise.common.sdk.http.callback

import okhttp3.Response

/**
 * 能够接触到sdk信息的原始callback，Response是OKHttp的类，在具体实现中，要把Response解析掉
 */
internal abstract class RawCallback {
    abstract fun onSuccess(resp: Response)
    abstract fun onError(exception: BusinessException)

    /**
     * application/json;charset=utf-8
     * 与服务端约定好，text/plain和application/json是String类型，其他都是文本。
     */
    fun isFile(resp: Response):Boolean{
        val contentType = resp.header("Content-Type")
        val type = contentType?.split(";")?.get(0)
        return !("text/plain" == type || "application/json" == type)
    }
}

/**
 * Response body被解析为字符串
 */
internal class StringCallBack(private val realCallback: DataCallBack) : RawCallback() {
    override fun onSuccess(resp: Response) {
        if (isFile(resp)) {
            val message = resp.body()?.string()
            if (message.isNullOrEmpty()) {
                realCallback.onFail(BusinessException("body empty!", BusinessException.CODE_BODY_EMPTY))
            } else {
                realCallback.onSuccess(message ?: " ")

            }
        }else{
            val input = resp.body()?.byteStream()
            if(input == null){
                realCallback.onFail(BusinessException("body empty!", BusinessException.CODE_BODY_EMPTY))
            }else{
                realCallback.onSuccess(input)
            }
        }
    }

    override fun onError(exception: BusinessException) {
        realCallback.onFail(exception)
    }

}