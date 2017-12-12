package com.arise.common.sdk.base

import android.support.v4.app.Fragment

/**
 * Fragment基类,功能不完善，显示方法还需要将Fragment提到最顶层。。
 */
abstract class BaseFragment : Fragment() {

    fun showFragment(fragment: BaseFragment) {
        if (childFragmentManager.fragments.contains(fragment)) {
            val ft = childFragmentManager.beginTransaction()
            ft.show(fragment)
            ft.commit()
            fragment.onVisibleChange(true)
        } else {
            throw Exception(fragment.getName() + "is not in this FragmentManager,please check!")
        }
    }

    fun hideFragment(fragment: BaseFragment) {
        if (childFragmentManager.fragments.contains(fragment)) {
            val ft = childFragmentManager.beginTransaction()
            ft.hide(fragment)
            ft.commit()
            fragment.onVisibleChange(false)
        } else {
            throw Exception(fragment.getName() + "is not in this FragmentManager,please check!")
        }
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