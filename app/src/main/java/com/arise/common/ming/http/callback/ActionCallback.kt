package com.arise.common.ming.http.callback

import com.arise.common.sdk.http.callback.BusinessException

/**
 * 最终网络请求回调
 */
interface ActionCallback<in T> {

    fun onError(exception: BusinessException)

    fun onSuccess(result: T)
}