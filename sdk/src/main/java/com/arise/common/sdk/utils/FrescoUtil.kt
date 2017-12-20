package com.arise.common.sdk.utils

import android.content.Context
import com.facebook.drawee.view.SimpleDraweeView

/**
 * 添加这个util只是为了提供一个统一管理的入口
 */
object FrescoUtil{

    fun loadNetPic(view:SimpleDraweeView,url:String){
        view.setImageURI(url)
    }

}