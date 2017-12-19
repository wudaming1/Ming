package com.arise.common.ming.user.info

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseActivity
import com.arise.common.ming.databinding.ActivityUserInfoBinding
import com.arise.common.ming.dialog.CommonDialog
import com.arise.common.sdk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : MyBaseActivity() {
    private lateinit var viewModule:UserInfoVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityUserInfoBinding>(this,R.layout.activity_user_info)
        viewModule = UserInfoVM(this)
        binding.viewModule = viewModule
        init()
    }

    private fun init() {
        title_bar.setTitle(getString(R.string.mine_info))
        group_nickname.setOnClickListener { showModifyNicknameDialog() }
        group_portrait.setOnClickListener { showModifyPortraitDialog() }

    }

    private fun showModifyNicknameDialog(){
        val dialog = CommonDialog.newInstance("修改昵称","testtesttesttest")
        dialog.cancel = {ToastUtil.showToast("取消修改昵称")}
        dialog.confirm = {ToastUtil.showToast("确认修改昵称")}
        dialog.show(fragmentManager,"nickname")
    }

    private fun showModifyPortraitDialog(){

    }
}
