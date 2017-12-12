package com.arise.common.ming.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.View
import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseActivity
import com.arise.common.ming.config.ConfigActivity
import com.arise.common.ming.home.userCenter.UserFragment
import com.arise.common.ming.user.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MyBaseActivity(),UserFragment.OnFragmentInteractionListener,DrawerLayout.DrawerListener {

    private lateinit var leftMenu: UserFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        go_config.setOnClickListener {
            val intent = Intent(this@MainActivity, ConfigActivity::class.java)
            this@MainActivity.startActivity(intent)

        }

        init()

    }

    private fun init() {
        leftMenu = UserFragment.newInstance("param1", "param2")
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.left_menu, leftMenu)
        ft.commit()

    }


    override fun onDrawerStateChanged(newState: Int) {
        if (newState == DrawerLayout.STATE_DRAGGING){
            leftMenu.refreshLoginState()
        }
    }

    override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
    }

    override fun onDrawerClosed(drawerView: View?) {
    }

    override fun onDrawerOpened(drawerView: View?) {
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
