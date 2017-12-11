package com.arise.common.ming.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置布局全屏展示
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}
