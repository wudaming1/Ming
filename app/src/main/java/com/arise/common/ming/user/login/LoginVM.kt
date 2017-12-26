package com.arise.common.ming.user.login

import android.databinding.ObservableField
import android.view.View
import android.widget.Toast
import com.arise.common.ming.MyApplication
import com.arise.common.ming.R
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.http.callback.ActionCallback
import com.arise.common.ming.user.UserInfoBean
import com.arise.common.ming.user.UserService


/**
 * 登录
 */
class LoginVM(val loginActivity: LoginActivity) : View.OnClickListener {


    var name = ObservableField<String>()
    var password = ObservableField<String>()


    override fun onClick(v: View) {
        if (name.get().isNullOrEmpty()) {
            Toast.makeText(MyApplication.instance, "用户名不能为空", Toast.LENGTH_LONG).show()
            return
        }

        if (password.get().isNullOrEmpty()) {
            Toast.makeText(MyApplication.instance, "密码不能为空", Toast.LENGTH_LONG).show()
            return
        }

        when (v.id) {
            R.id.login -> {
                UserService.login(name.get(), password.get(), object : ActionCallback<String> {
                    override fun onSuccess(result: String?) {
                        result?.apply {
                            MyApplication.instance.updateToken(result.toString())
                            authSuccess()
                        }
                    }

                })
            }
            R.id.register -> {
                UserService.register(name.get(), password.get(), object : ActionCallback<String> {
                    override fun onSuccess(result: String?) {
                        result?.apply {
                            MyApplication.instance.updateToken(result.toString())
                            authSuccess()
                        }
                    }
                })
            }
        }
    }

    private fun authSuccess() {
        UserService.getUserInfo(object : ActionCallback<UserInfoBean> {
            override fun onSuccess(result: UserInfoBean?) {
                result?.let {
                    UserConfig.login(result)
                    loginActivity.finish()
                }
            }
        }
        )
    }
}