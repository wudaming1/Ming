package com.arise.common.ming.user.login

import android.database.DatabaseUtils
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arise.common.ming.R
import com.arise.common.ming.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bing = DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)
        bing.viewModule = LoginVM()
    }
}
