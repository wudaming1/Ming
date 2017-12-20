package com.arise.common.ming

import android.graphics.Color
import android.os.Build
import com.arise.common.ming.base.MyImageLoader
import com.arise.common.ming.base.PreferenceManager
import com.arise.common.ming.config.FrescoConfig
import com.arise.common.sdk.BaseApplication
import com.arise.common.sdk.http.HttpManager
import com.meili.component.imagepicker.MLImagePicker
import com.meili.component.imagepicker.view.CropImageView
import kotlin.properties.Delegates


/**
 * Created by wudaming on 2017/11/17.
 */
class MyApplication : BaseApplication() {

    companion object {
        var instance: MyApplication by Delegates.notNull()
        var httpManager: HttpManager by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        httpManager = HttpManager(httpClient)

        initHeader()
        FrescoConfig().init(this)
        initImagePicker()
    }


    private fun initHeader() {
        headers.put("token", PreferenceManager.getToken())
        headers.put("os", "android")
        headers.put("version_name", BuildConfig.VERSION_NAME)
        headers.put("version_code", BuildConfig.VERSION_CODE.toString())
        headers.put("brand", Build.BRAND)
    }



    private fun initImagePicker() {
        MLImagePicker.getInstance()
                .setImageLoadFrame(MyImageLoader())
                .setImageMaxSize(9)
                .setChooseType(MLImagePicker.TYPE_CHOOSE_MULTIPLE)
                .setNavigationIconRes(0)
                .setOutputY(800)
                .setOutputX(800)
                .setFocusHeight(800)
                .setFocusWeight(800)
                .setLight(false)
                .setToolbarColor(Color.BLACK)
                .setStatusBarColor(Color.BLACK)
                .setFocusStyle(CropImageView.Style.CIRCLE).isSaveRectangle = true
    }


}