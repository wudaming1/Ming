package com.arise.common.ming

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arise.common.ming.config.HttpConfig
import com.arise.common.ming.base.HttpResultBean
import com.arise.common.ming.config.ConfigActivity
import com.arise.common.ming.user.login.LoginActivity
import com.arise.common.sdk.http.HttpManager
import com.arise.common.sdk.http.callback.BusinessException
import com.arise.common.sdk.http.callback.DataCallBack
import com.arise.common.sdk.utils.FileUtil
import com.arise.common.sdk.utils.JsonUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        go_login.setOnClickListener {
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            this@MainActivity.startActivity(intent)
        }

        go_config.setOnClickListener {
            val intent = Intent(this@MainActivity,ConfigActivity::class.java)
            this@MainActivity.startActivity(intent)

        }

    }

    override fun onClick(v: View) {

    }
}
