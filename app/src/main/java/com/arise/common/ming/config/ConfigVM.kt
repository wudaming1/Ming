package com.arise.common.ming.config

import android.databinding.ObservableField
import android.view.View

/**
 * 配置服务器地址
 */
class ConfigVM(private val activity: ConfigActivity):View.OnClickListener{

    var url = ObservableField<String>()

    init {
        url.set(HttpConfig.base_url)
    }



    override fun onClick(v: View) {
        HttpConfig.modifyBaseUrl(url.get()!!)
        activity.finish()
    }

}