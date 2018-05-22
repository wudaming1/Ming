package com.arise.common.ming.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.arise.common.ming.R
import com.arise.common.sdk.base.BaseActivity
import kotlinx.android.synthetic.main.page_container.*
import kotlinx.android.synthetic.main.page_container.view.*

open class MyBaseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置布局全屏展示
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.setContentView(R.layout.page_container)


    }

    fun initTitle(show: Boolean = true, title: String = "") {
        title_bar.visibility = if (show) View.VISIBLE else View.GONE
        setTitle(title)
    }

    override fun setTitle(id: Int) {
        title_bar.setTitle(id)
    }

    override fun setContentView(layoutResID: Int) {
        real_content.removeAllViews()
        val inflater = LayoutInflater.from(this)
        inflater.inflate(layoutResID, real_content, true)
    }

    override fun setContentView(view: View?) {
        view?.apply {
            real_content.removeAllViews()
            real_content.addView(this)
        }
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        if (params == null) {
            setContentView(view)
        } else {
            if (params is FrameLayout.LayoutParams) {
                view?.apply {
                    real_content.removeAllViews()
                    real_content.addView(this, params)
                }
            } else {
                throw RuntimeException("params 必须是 FrameLayout.LayoutParams！")
            }
        }
    }
}
