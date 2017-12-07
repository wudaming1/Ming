package com.arise.common.ming.user.login

import android.databinding.ObservableField
import android.view.View
import android.widget.Toast
import com.arise.common.ming.MyApplication
import com.arise.common.ming.R
import com.arise.common.ming.http.HttpResultBean
import com.arise.common.ming.config.HttpConfig
import com.arise.common.ming.http.Method
import com.arise.common.ming.http.MyHttpRequest
import com.arise.common.ming.http.callback.ActionCallback
import com.arise.common.sdk.http.HttpManager
import com.arise.common.sdk.http.callback.BusinessException
import com.arise.common.sdk.http.callback.DataCallBack
import com.arise.common.sdk.utils.JsonUtil

/**
 * 登录
 */
class LoginVM : View.OnClickListener {

    private val login_url = HttpConfig.base_url + "/login"
    private val register_url = HttpConfig.base_url + "/register"

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
                MyHttpRequest<String>(login_url, object : ActionCallback {
                    override fun onError(exception: BusinessException) {
                        Toast.makeText(MyApplication.instance, exception.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onSuccess(result: Any?) {
                        result?.apply {
                            Toast.makeText(MyApplication.instance, result.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                }, Method.POST).execute()
            }
            R.id.register -> {
                MyHttpRequest<String>(register_url, object : ActionCallback {
                    override fun onError(exception: BusinessException) {
                        Toast.makeText(MyApplication.instance, exception.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onSuccess(result: Any?) {
                        result?.apply {
                            Toast.makeText(MyApplication.instance, result.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                }, Method.POST).execute()
            }
        }
    }
}