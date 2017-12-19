package com.arise.common.sdk.base

import android.support.v7.app.AppCompatActivity

/**
 * Activity基类
 */
open class BaseActivity : AppCompatActivity() {

    fun showFragment(fragment: BaseFragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.show(fragment)
        ft.commit()
        fragment.onVisibleChange(true)

    }

    fun hideFragment(fragment: BaseFragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.hide(fragment)
        ft.commit()
        fragment.onVisibleChange(false)

    }

}