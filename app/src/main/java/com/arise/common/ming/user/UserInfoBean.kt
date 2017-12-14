package com.arise.common.ming.user

import com.arise.common.ming.base.BaseBean

/**
 * Created by wudaming on 2017/12/12.
 */
data class UserInfoBean(val userName:String,
                        val imgUrl:String? = null,
                        val sex:String,
                        val birthday:Long):BaseBean()