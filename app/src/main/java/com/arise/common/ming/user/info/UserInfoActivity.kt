package com.arise.common.ming.user.info

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseActivity
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.databinding.ActivityUserInfoBinding
import com.arise.common.ming.dialog.CommonDialog
import com.arise.common.ming.dialog.EditDialog
import com.arise.common.sdk.utils.ToastUtil
import com.meili.component.imagepicker.MLImagePicker
import com.meili.component.imagepicker.bean.ImageBean
import com.meili.component.imagepicker.ui.MLImageListActivity
import kotlinx.android.synthetic.main.activity_user_info.*
import java.io.File

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
        login_out.setOnClickListener { showLoginOutDialog() }
    }

    private fun showModifyNicknameDialog(){
        val dialog = EditDialog.newInstance("修改昵称","请输入昵称")
        dialog.confirm = { viewModule.modifyNickname(it) }
        dialog.show(fragmentManager,"nickname")
    }

    private fun showModifyPortraitDialog(){
        MLImagePicker.getInstance().chooseType = MLImagePicker.TYPE_CHOOSE_SINGLE
        val intent = Intent(this, MLImageListActivity::class.java)
        startActivityForResult(intent, MLImagePicker.CODE_REQUEST_IMG_LIST)
    }

    private fun showLoginOutDialog(){
        val dialog = CommonDialog.newInstance(getString(R.string.login_out))
        dialog.confirm = {
            UserConfig.loginOut()
        }
        dialog.show(fragmentManager,"nickname")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MLImagePicker.CODE_REQUEST_IMG_LIST){
            if (resultCode == MLImagePicker.CODE_RESULT_IMG_LIST){
                data?.let {
                    val imgList = data.getParcelableArrayListExtra<ImageBean>(MLImagePicker.RESULT_IMG_LIST)
                    viewModule.modifyPortrait(File(imgList[0].imgPath))
                }
            }
        }
    }
}
