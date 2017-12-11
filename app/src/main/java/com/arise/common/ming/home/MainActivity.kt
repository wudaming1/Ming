package com.arise.common.ming.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseActivity
import com.arise.common.ming.config.ConfigActivity
import com.arise.common.ming.user.login.LoginActivity

class MainActivity : MyBaseActivity(), View.OnClickListener {

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
