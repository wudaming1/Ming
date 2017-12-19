package com.arise.common.ming

import android.os.Build
import android.view.View
import com.arise.common.sdk.utils.DensityUtil

/**
 * 沉浸式状态栏，全屏模式工具类
 */
object ImmersiveUtil {
     private fun getStatusBarHeightForImmersive(): Int {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            0
        } else {
            DensityUtil.getStatusBarHeight()
        }
    }

    /**
     * @param view:当前页面的顶层view，使用这个view的topPadding来给状态栏留出空间
     */
    fun makeSpaceForImmersive(view: View){
        view.setPadding(view.paddingLeft,view.paddingTop+getStatusBarHeightForImmersive(),view.paddingRight ,view.paddingBottom)
    }
}