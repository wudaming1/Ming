package com.arise.common.ming.user.login

import android.content.Intent
import android.database.DatabaseUtils
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arise.common.ming.MyApplication
import com.arise.common.ming.R
import com.arise.common.ming.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    companion object {
       fun goLogin(){
           val intent = Intent(MyApplication.instance,LoginActivity::class.java)
           MyApplication.instance.startActivity(intent)
       }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bing = DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)
        bing.viewModule = LoginVM(this)
    }
}
