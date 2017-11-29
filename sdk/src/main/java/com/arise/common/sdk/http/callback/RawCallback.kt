package com.arise.common.sdk.http.callback

import okhttp3.Response

/**
 * 能够接触到sdk信息的原始callback，Response是OKHttp的类，在具体实现中，要把Response解析掉
 */
internal interface RawCallback {
    fun onSuccess(result: Response)
    fun onError(exception: BusinessException)

}