package com.arise.common.ming.base

import com.arise.common.sdk.base.BaseFragment

/**
 * Created by wudaming on 2017/12/11.
 */
abstract class MyBaseFragment:BaseFragment(){


    /**
     * 在这里做一个空实现，并不是所有Fragment都关心这个事件
     */
    override fun onVisibleChange(visible: Boolean) {
    }
}