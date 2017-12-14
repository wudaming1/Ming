package com.arise.common.sdk.base

import android.support.v4.app.Fragment

/**
 * Fragment基类,功能不完善，显示方法还需要将Fragment提到最顶层。。
 */
abstract class BaseFragment : Fragment() {

    fun showFragment(fragment: BaseFragment) {
        val ft = childFragmentManager.beginTransaction()
        ft.show(fragment)
        ft.commit()
        fragment.onVisibleChange(true)
    }

    fun hideFragment(fragment: BaseFragment) {
        val ft = childFragmentManager.beginTransaction()
        ft.hide(fragment)
        ft.commit()
        fragment.onVisibleChange(false)
    }

    override fun onStart() {
        super.onStart()
        onVisibleChange(true)
    }

    override fun onStop() {
        super.onStop()
        onVisibleChange(false)
    }

    abstract fun onVisibleChange(visible:Boolean)

    abstract fun getName(): String

}