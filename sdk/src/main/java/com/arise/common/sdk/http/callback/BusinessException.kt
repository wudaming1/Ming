package com.arise.common.sdk.http.callback

/**
 * Created by wudaming on 2017/11/17.
 */

class BusinessException(override var message: String, val code: Int) : Exception(message) {
    companion object {
        //http状态码到600，所以，新增业务逻辑的错误码从700开始，避免冲突
        val CODE_NO_NET_ERROR = 700
        val MESSAGE_NO_NET_ERROR = "断网了"

        val CODE_CANCEL = 701
        val CODE_BODY_EMPTY = 702
    }
}