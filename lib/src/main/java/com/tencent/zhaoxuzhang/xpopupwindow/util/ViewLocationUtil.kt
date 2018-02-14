package com.tencent.zhaoxuzhang.xpopupwindow.util

import android.view.View

/**
 * Created by Xu on 2018/2/14.
 */
object ViewLocationUtil {

    fun getViewLocationArr(view: View): IntArray {
        var viewLoc = intArrayOf(0, 0)
        view.getLocationOnScreen(viewLoc)
        return viewLoc
    }

}