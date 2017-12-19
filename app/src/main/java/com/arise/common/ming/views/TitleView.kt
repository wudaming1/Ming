package com.arise.common.ming.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.arise.common.ming.ImmersiveUtil
import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseActivity

/**
 * 为了实现全屏模式，即不显示actionbar，替代actionbar的view
 * 目前自能添加到Activity，还不支持Fragment
 */
class TitleView : FrameLayout {


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }


    private fun init() {
        View.inflate(context, R.layout.title_layout, this)
        ImmersiveUtil.makeSpaceForImmersive(findViewById(R.id.parent))
        findViewById<View>(R.id.go_back).setOnClickListener {
            //todo 添加Fragment支持
            if (context is MyBaseActivity) {
                (context as MyBaseActivity).finish()

            }
        }

    }

    fun setTitle(title: String) {
        (findViewById<TextView>(R.id.title)).setText(title)
    }


}
