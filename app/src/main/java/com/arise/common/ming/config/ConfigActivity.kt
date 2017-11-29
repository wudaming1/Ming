package com.arise.common.ming.config

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.arise.common.ming.R
import com.arise.common.ming.databinding.ActivityConfigBinding

class ConfigActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityConfigBinding>(this,R.layout.activity_config)
        binding.viewModule = ConfigVM(this)
    }
}
