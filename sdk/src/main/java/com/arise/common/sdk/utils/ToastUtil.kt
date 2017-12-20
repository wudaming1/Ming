package com.arise.common.sdk.utils

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.Toast
import com.arise.common.sdk.BaseApplication
import java.util.*

/**
 * toast的样式不需要自定义，用系统的就行，不过可以使用 {@link android.widget.Toast#setview} api完全自定义界面
 *
 *
 * 这个工具要处理的问题：
 * 1. 短时间多次触发同一个toast:在当前Toast显示中的情况下，忽略1s内的事件
 * 2. 短时间多次触发多个toast:取消当前toast，新生成一个
 */
object ToastUtil {

    private var currentMessage = ""
    private var toast:Toast? = null
    private val timer = object :CountDownTimer(1000,1000){
        override fun onFinish() {
            currentMessage = ""
        }

        override fun onTick(millisUntilFinished: Long) {
        }

    }

    @SuppressLint("ShowToast")
    fun showToast(message: String) {
        if (toast == null){
            toast = Toast.makeText(BaseApplication.baseInstance, currentMessage, Toast.LENGTH_LONG)
        }
        if (currentMessage != message){
            timer.cancel()
            currentMessage = message
            toast?.let {
                it.setText(currentMessage)
                it.show()
                timer.start()
            }
        }

    }

    fun cancel(){
        toast?.cancel()
        toast = null
    }

}