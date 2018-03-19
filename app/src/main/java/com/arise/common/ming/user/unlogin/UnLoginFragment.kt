package com.arise.common.ming.user.unlogin


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseFragment
import com.arise.common.ming.user.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_unlogin.*


/**
 * 未登录小模块，内容居中，仅实现跳转登录页面。
 */
class UnLoginFragment : MyBaseFragment() {

    private val TAG = UnLoginFragment::class.java.simpleName


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unlogin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        login.setOnClickListener {
            LoginActivity.goLogin()
        }
    }



    override fun getName(): String {
        return TAG
    }

}
