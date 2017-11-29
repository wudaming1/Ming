package com.arise.common.ming.base

/**
 * @param resultCode:0-请求成功;1-未登录;其他待定
 */
data class HttpResultBean(var resultCode: Int = 0
                          ,var data:String = ""
                          ,var message:String = "") : BaseBean() {
}