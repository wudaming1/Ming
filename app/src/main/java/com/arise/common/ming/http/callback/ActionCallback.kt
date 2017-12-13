package com.arise.common.ming.http.callback

import android.widget.Toast
import com.arise.common.ming.MyApplication
import com.arise.common.sdk.http.callback.BusinessException

/**
 * 最终网络请求回调
 */
interface ActionCallback {

    fun onError(exception: BusinessException) {
        Toast.makeText(MyApplication.instance, exception.message, Toast.LENGTH_LONG).show()
    }

    fun onSuccess(result: Any?)
}