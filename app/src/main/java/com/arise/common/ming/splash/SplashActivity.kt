package com.arise.common.ming.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.arise.common.ming.R
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.home.MainActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null
    private var showTime = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        textView3.setOnClickListener {
//            goMain()
//        }
        textView3.text = "跳过 ${showTime - 1} S"
        initTimer()
        synchronizingServer()
    }

    private fun initTimer() {
        timer = object : CountDownTimer(showTime *1000L, 1000) {
            override fun onFinish() {
                goMain()
            }

            override fun onTick(millisUntilFinished: Long) {
                val time = --showTime - 1
                textView3.text = "跳过 ${time} S"
            }

        }
        timer?.start()
    }

    private fun goMain() {
        timer?.cancel()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    /**
     * 同步服务器，更新到最新的信息
     */
    private fun synchronizingServer(){
        if (UserConfig.isLogin){

        }
    }
}
