package com.arise.common.ming.user.info

import android.databinding.ObservableField
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.http.callback.ActionCallback
import com.arise.common.ming.user.UserService
import com.arise.common.sdk.utils.ToastUtil
import java.io.File

/**
 * Created by wudaming on 2017/12/18.
 */
class UserInfoVM(activity: UserInfoActivity){

    var nickname = ObservableField<String>()
    var imgUrl = ObservableField<String>()

    init {
        UserConfig.user?.let {
            nickname.set(it.userName)
            imgUrl.set(it.imgUrl)
        }
    }

    fun modifyNickname(string: String){
        UserService.modifyUserInfo(userName = string,callback = object :ActionCallback<String>{
            override fun onSuccess(result: String?) {
                result?.let {
                    nickname.set(it)
                    UserConfig.user?.let { it.userName = result }
                    ToastUtil.showToast("修改成功！")
                }
            }

        })

    }

    /**
     * 修改头像
     */
    fun modifyPortrait(file: File){
        UserService.modifyPortrait(file,object :ActionCallback<String>{
            override fun onSuccess(result: String?) {
                result?.let {
                    UserConfig.user?.let { it.imgUrl = result }
                    imgUrl.set(result)
                    ToastUtil.showToast("修改成功！")
                }
            }

        })
    }

}