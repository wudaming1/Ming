package com.arise.common.ming.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arise.common.sdk.base.BaseFragment
import com.orhanobut.logger.Logger

/**
 * Created by wudaming on 2017/12/11.
 */
abstract class MyBaseFragment:BaseFragment(){


    /**
     * 在这里做一个空实现，并不是所有Fragment都关心这个事件
     */
    override fun onVisibleChange(visible: Boolean) {
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.e( "${getName()} onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.e( "${getName()} onCreate")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.e( "${getName()} onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logger.e( "${getName()} onActivityCreated")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.e( "${getName()} onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.e( "${getName()} onDetach")
    }
}