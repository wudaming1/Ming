package com.arise.common.ming.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseActivity
import com.arise.common.ming.config.ConfigActivity
import com.arise.common.ming.home.userCenter.UserFragment
import com.arise.common.sdk.utils.ToastUtil
import com.meili.component.imagepicker.MLImagePicker
import com.meili.component.imagepicker.ui.MLImageListActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MyBaseActivity(),UserFragment.OnFragmentInteractionListener {

    private lateinit var leftMenu: UserFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        go_config.setOnClickListener {
            val intent = Intent(this@MainActivity, ConfigActivity::class.java)
            this@MainActivity.startActivity(intent)

        }

        toast_same.setOnClickListener {
            val intent = Intent(this, MLImageListActivity::class.java)
            startActivityForResult(intent, MLImagePicker.CODE_REQUEST_IMG_LIST)
        }

        toast_diff.setOnClickListener { ToastUtil.showToast("我是不同的${System.currentTimeMillis()}") }

        init()

    }

    private fun init() {
        leftMenu = UserFragment.newInstance("param1", "param2")
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.left_menu, leftMenu)
        ft.commit()

    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
