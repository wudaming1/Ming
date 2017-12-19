package com.arise.common.ming.user.login

import android.databinding.ObservableField
import android.view.View
import android.widget.Toast
import com.arise.common.ming.MyApplication
import com.arise.common.ming.R
import com.arise.common.ming.base.MessageEvent
import com.arise.common.ming.base.PreferenceManager
import com.arise.common.ming.config.HttpConfig
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.http.Method
import com.arise.common.ming.http.MyHttpRequest
import com.arise.common.ming.http.callback.ActionCallback
import com.arise.common.ming.user.UserInfoBean
import org.greenrobot.eventbus.EventBus


/**
 * 登录
 */
class LoginVM(val loginActivity: LoginActivity) : View.OnClickListener {

    private val loginUrl = HttpConfig.base_url + "/login"
    private val registerUrl = HttpConfig.base_url + "/register"
    private val userInfoUrl = HttpConfig.base_url + "/auth/userInfo"

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

        val map = HashMap<String, String>()
        map.put("name", name.get())
        map.put("password", password.get())
        when (v.id) {
            R.id.login -> {
                MyHttpRequest<String>(loginUrl, map, object : ActionCallback {
                    override fun onSuccess(result: Any?) {
                        result?.apply {
                            authSuccess(result.toString())
                        }
                    }

                }, Method.POST).execute()
            }
            R.id.register -> {
                MyHttpRequest<String>(registerUrl, map, object : ActionCallback {
                    override fun onSuccess(result: Any?) {
                        result?.apply {
                            authSuccess(result.toString())
                        }
                    }

                }, Method.POST).execute()
            }
        }
    }

    private fun authSuccess(token: String) {
        PreferenceManager.saveToken(token)
        MyHttpRequest<UserInfoBean>(userInfoUrl, object : ActionCallback {
            override fun onSuccess(result: Any?) {
                result?.let {
                    UserConfig.user = result as UserInfoBean
                    UserConfig.isLogin = true
                    PreferenceManager.saveLoginState(true)
                    EventBus.getDefault().post(MessageEvent.LOGIN)
                    loginActivity.finish()
                }
            }
        }, Method.GET).setclazz(UserInfoBean::class.java).execute()

    }
}