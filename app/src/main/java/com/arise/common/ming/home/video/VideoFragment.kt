package com.arise.common.ming.home.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arise.common.ming.R
import com.arise.common.ming.base.MyBaseFragment

/**
 * Created by wudaming on 2018/2/26.
 */
class VideoFragment : MyBaseFragment() {

    val TAG = VideoFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //这个地方一定要有false标记，不然在操作Fragment时，指定的view会报已有父控件的错误
        return layoutInflater.inflate(R.layout.fragment_video, container,false)
    }

    override fun getName(): String {
        return TAG
    }
}