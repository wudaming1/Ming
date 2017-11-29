package com.arise.common.ming

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arise.common.ming.base.HttpConfig
import com.arise.common.ming.base.HttpResultBean
import com.arise.common.sdk.http.HttpManager
import com.arise.common.sdk.http.callback.DataCallBack
import com.arise.common.sdk.http.callback.BusinessException
import com.arise.common.sdk.utils.FileUtil
import com.arise.common.sdk.utils.JsonUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val url = HttpConfig.base_url
    private val fileUrl = url+"jenkins_api_document"
    private val filePath = FileUtil.NET_CACHE_PATH + "/file"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        string_request.setOnClickListener {
            HttpManager.instance.doGet(url, object : DataCallBack<String> {
                override fun onFail(exception: BusinessException) {
                    Log.e("wdm", exception.message)
                }

                override fun onSuccess(result: String) {
                    val resultBean = JsonUtil.readValue<HttpResultBean>(result)
                    Log.e("wdm", "String:" + result + "\nbean:" + resultBean.toString())

                }

            })
        }

        file_request.setOnClickListener {
            HttpManager.instance.doGet(fileUrl,filePath, object : DataCallBack<File> {
                override fun onFail(exception: BusinessException) {
                    Log.e("wdm", exception.message)
                }

                override fun onSuccess(result: File) {
                    Toast.makeText(applicationContext,result.absolutePath,Toast.LENGTH_LONG).show()
                }

            })
        }

    }

    override fun onClick(v: View) {

    }
}
