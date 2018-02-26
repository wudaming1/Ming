package com.arise.common.ming.user.info

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.MediaStore
import com.arise.common.imagepicker.ImagePicker
import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseActivity
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.databinding.ActivityUserInfoBinding
import com.arise.common.ming.dialog.CommonDialog
import com.arise.common.ming.dialog.EditDialog
import kotlinx.android.synthetic.main.activity_user_info.*
import java.io.File

class UserInfoActivity : MyBaseActivity() {

    private lateinit var viewModule: UserInfoVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityUserInfoBinding>(this, R.layout.activity_user_info)
        viewModule = UserInfoVM(this)
        binding.viewModule = viewModule
        init()
    }

    private fun init() {
        title_bar.setTitle(getString(R.string.mine_info))
        group_nickname.setOnClickListener { showModifyNicknameDialog() }
        group_portrait.setOnClickListener { showModifyPortraitDialog() }
        login_out.setOnClickListener { showLogoutDialog() }
    }

    private fun showModifyNicknameDialog() {
        val dialog = EditDialog.newInstance("修改昵称", "请输入昵称")
        dialog.confirm = { viewModule.modifyNickname(it) }
        dialog.show(fragmentManager, "nickname")
    }

    private fun showModifyPortraitDialog() {
        ImagePicker.getInstance().pick(this, 1000)
    }

    private fun showLogoutDialog() {
        val dialog = CommonDialog.newInstance(getString(R.string.login_out))
        dialog.confirm = {
            UserConfig.loginOut()
            finish()
        }
        dialog.show(fragmentManager, "nickname")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            //EXP:   content://media/external/file/287435
            val selectedImage = data.data
            val filePathColumns = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage, filePathColumns, null, null, null)
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumns[0])
            val path = cursor.getString(columnIndex)
            val file = File(path)
            viewModule.modifyPortrait(file)
            cursor.close()
        }

    }
}
