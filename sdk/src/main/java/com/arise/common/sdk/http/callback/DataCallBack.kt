package com.arise.common.sdk.http.callback

/**
 * Response已经被解析为T类型的Data
 */
interface DataCallBack<in T>{
    fun onFail(exception: BusinessException)

    fun onSuccess(resp: T)
}