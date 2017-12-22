package com.arise.common.sdk.http.callback

import com.arise.common.sdk.BaseApplication
import okhttp3.Response

/**
 * 能够接触到sdk信息的原始callback，Response是OKHttp的类，在具体实现中，要把Response解析掉
 */
internal abstract class RawCallback {
    abstract fun onSuccess(result: Response)
    abstract fun onError(exception: BusinessException)

    fun postOnUIThread(r:()->Unit) {
        BaseApplication.mainHandler.post(r)
    }
}

/**
 * Response body被解析为字符串
 */
internal class StringCallBack(private val realCallback: DataCallBack<String>) : RawCallback() {
    override fun onSuccess(result: Response) {
        val message = result.body()?.string()
        if (message.isNullOrEmpty()) {

            postOnUIThread {
                realCallback.onFail(BusinessException("body empty!", BusinessException.CODE_BODY_EMPTY))
            }
        } else {
            postOnUIThread { realCallback.onSuccess(message ?: " ") }

        }
    }

    override fun onError(exception: BusinessException) {
        postOnUIThread { realCallback.onFail(exception) }
    }

}