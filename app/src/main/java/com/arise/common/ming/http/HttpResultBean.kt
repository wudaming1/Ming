package com.arise.common.ming.http

import com.arise.common.ming.base.BaseBean

/**
 *
 */
//data class HttpResultBean(var resultCode: Int = HttpResultCode.FAIL
//                          ,var data:String = ""
//                          ,var message:String = "") : BaseBean() {
//}

data class HttpResultBean(var status: Int = ResultCode.SUCCESS
                        , var data: String = ""
                        , var error: ErrorBean? = null): BaseBean()

object ResultCode {
    val SUCCESS = 0
    val FAIL = -1
}


data class ErrorBean(var code: Int = ErrorCode.UNKNOWN,
                     var data: String = "",
                     var message: String = "")


object ErrorCode {


    val UNKNOWN = 0
    val PARAM_ERR = 1
    val DATABASE_ERR = 2
    val TOKEN_INVALID = 3

}