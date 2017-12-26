package com.arise.common.ming.user

import com.arise.common.ming.base.BaseBean

/**
 * Created by wudaming on 2017/12/12.
 */
data class UserInfoBean(var userName:String,
                        var imgUrl:String? = null,
                        val sex:String?=null,
                        val birthday:Long?=null):BaseBean()