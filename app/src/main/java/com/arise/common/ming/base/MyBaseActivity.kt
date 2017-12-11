package com.arise.common.ming.base

import android.os.Bundle
import android.view.Window
import com.arise.common.sdk.base.BaseActivity

open class MyBaseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置布局全屏展示
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}
