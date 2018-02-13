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

}