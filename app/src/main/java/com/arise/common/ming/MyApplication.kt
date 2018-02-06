package com.arise.common.ming

//import io.realm.Realm
import android.os.Build
import com.arise.common.ming.base.PreferenceManager
import com.arise.common.ming.config.FrescoConfig
import com.arise.common.sdk.BaseApplication
import com.arise.common.sdk.http.HttpManager
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
//        Realm.init(this)
    }


    private fun initHeader() {
        headers.put("token", PreferenceManager.getToken())
        headers.put("os", "android")
        headers.put("version_name", BuildConfig.VERSION_NAME)
        headers.put("version_code", BuildConfig.VERSION_CODE.toString())
        headers.put("brand", Build.BRAND)
    }

    fun updateToken(token:String){
        PreferenceManager.saveToken(token)
        headers.put("token", PreferenceManager.getToken())
    }



    private fun initImagePicker() {

    }


}