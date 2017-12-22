package com.arise.common.ming.http.callback

import com.arise.common.sdk.http.callback.BusinessException
import com.arise.common.sdk.utils.ToastUtil

/**
 * 最终网络请求回调
 */
interface ActionCallback<in T> {

    fun onError(exception: BusinessException) {
        ToastUtil.showToast(exception.message)
    }

    fun onSuccess(result: T?)
}