package com.arise.common.ming.config

import android.databinding.ObservableField
import android.view.View
import com.arise.common.ming.base.PreferenceManager

/**
 * 配置服务器地址
 */
class ConfigVM(private val activity: ConfigActivity):View.OnClickListener{

    var url = ObservableField<String>()

    init {
        url.set(HttpConfig.base_url)
    }



    override fun onClick(v: View) {
        HttpConfig.base_url = url.get()
        PreferenceManager.saveDebugHttpUrl(HttpConfig.base_url)
        activity.finish()
    }

}