package com.arise.common.ming.user.login

import android.databinding.ObservableField
import android.view.View
import android.widget.Toast
import com.arise.common.ming.MyApplication
import com.arise.common.ming.R
import com.arise.common.ming.base.HttpResultBean
import com.arise.common.ming.config.HttpConfig
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
        if (name.get().isNullOrEmpty()){
            Toast.makeText(MyApplication.instance, "用户名不能为空", Toast.LENGTH_LONG).show()
            return
        }

        if (password.get().isNullOrEmpty()){
            Toast.makeText(MyApplication.instance, "密码不能为空", Toast.LENGTH_LONG).show()
            return
        }

        val map = HashMap<String, String>()
        map.put("name", name.get())
        map.put("password", password.get())
        when (v.id) {
            R.id.login -> {
                HttpManager.instance.doPost(login_url, map, object : DataCallBack<String> {
                    override fun onFail(exception: BusinessException) {
                        Toast.makeText(MyApplication.instance, exception.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onSuccess(result: String) {
                        val resultBean = JsonUtil.readValue<HttpResultBean>(result)
                        Toast.makeText(MyApplication.instance, resultBean.toString(), Toast.LENGTH_LONG).show()

                    }

                })
            }
            R.id.register -> {
                HttpManager.instance.doPost(register_url, map, object : DataCallBack<String> {
                    override fun onFail(exception: BusinessException) {
                        Toast.makeText(MyApplication.instance, exception.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onSuccess(result: String) {
                        val resultBean = JsonUtil.readValue<HttpResultBean>(result)
                        Toast.makeText(MyApplication.instance, resultBean.toString(), Toast.LENGTH_LONG).show()

                    }

                })
            }
        }
    }
}