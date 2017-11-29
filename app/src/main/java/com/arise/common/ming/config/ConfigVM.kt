package com.arise.common.ming.config

import android.databinding.ObservableField
import android.view.View

/**
 * Created by wudaming on 2017/11/29.
 */
class ConfigVM(private val activity: ConfigActivity):View.OnClickListener{

    var url = ObservableField<String>()

    init {
        url.set(HttpConfig.base_url)
    }



    override fun onClick(v: View) {
        HttpConfig.base_url = url.get()
        activity.finish()
    }

}