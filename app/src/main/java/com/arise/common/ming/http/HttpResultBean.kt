package com.arise.common.ming.http

import com.arise.common.ming.base.BaseBean

/**
 *
 */
data class HttpResultBean(var resultCode: Int = HttpResultCode.FAIL
                          ,var data:String = ""
                          ,var message:String = "") : BaseBean() {
}