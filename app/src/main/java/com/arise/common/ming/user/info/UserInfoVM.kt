package com.arise.common.ming.user.info

import android.databinding.ObservableField
import com.arise.common.ming.config.UserConfig
import java.io.File

/**
 * Created by wudaming on 2017/12/18.
 */
class UserInfoVM(activity: UserInfoActivity){

    var nickname = ObservableField<String>()

    init {
        UserConfig.user?.let { nickname.set(it.userName) }
    }

    fun modifyNickname(string: String){

    }

    /**
     *
     */
    fun modifyPortrait(file: File){

    }

}