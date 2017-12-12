package com.arise.common.sdk.base

import android.support.v7.app.AppCompatActivity

/**
 * Activity基类
 */
open class BaseActivity:AppCompatActivity(){

    fun showFragment(fragment: BaseFragment) {
        if (supportFragmentManager.fragments.contains(fragment)) {
            val ft = supportFragmentManager.beginTransaction()
            ft.show(fragment)
            ft.commit()
            fragment.onVisibleChange(true)
        } else {
            throw Exception(fragment.getName() + "is not in this FragmentManager,please check!")
        }
    }

    fun hideFragment(fragment: BaseFragment) {
        if (supportFragmentManager.fragments.contains(fragment)) {
            val ft = supportFragmentManager.beginTransaction()
            ft.hide(fragment)
            ft.commit()
            fragment.onVisibleChange(false)
        } else {
            throw Exception(fragment.getName() + "is not in this FragmentManager,please check!")
        }
    }

}