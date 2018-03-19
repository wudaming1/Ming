package com.arise.common.ming.home

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentTabHost
import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseActivity
import com.arise.common.ming.component.IntensiveFragmentTabHost
import com.arise.common.ming.home.music.MusicFragment
import com.arise.common.ming.home.news.NewsFragment
import com.arise.common.ming.home.userCenter.UserFragment
import com.arise.common.ming.home.video.VideoFragment


class MainActivity : MyBaseActivity(), UserFragment.OnFragmentInteractionListener {

    private lateinit var mTabHost: IntensiveFragmentTabHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        init()

    }

    private fun initView() {
        mTabHost = findViewById(R.id.tabhost)
        mTabHost.setup(this, supportFragmentManager, R.id.fragment)
        mTabHost.addTab(mTabHost.newTabSpec("news").setIndicator("新闻"), NewsFragment::class.java, null)
        mTabHost.addTab(mTabHost.newTabSpec("video").setIndicator("视频"), VideoFragment::class.java, null)
        mTabHost.addTab(mTabHost.newTabSpec("music").setIndicator("音乐"), MusicFragment::class.java, null)

    }

    private fun init() {
        mTabHost.currentTab = 0

    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
