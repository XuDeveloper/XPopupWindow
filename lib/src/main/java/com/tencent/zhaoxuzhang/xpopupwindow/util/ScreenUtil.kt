package com.tencent.zhaoxuzhang.xpopupwindow.util

import android.content.Context

/**
 * Created by Xu on 2018/2/13.
 */
object ScreenUtil {

    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        var resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}