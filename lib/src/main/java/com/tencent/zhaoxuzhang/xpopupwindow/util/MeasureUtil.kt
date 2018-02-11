package com.tencent.zhaoxuzhang.xpopupwindow.util

import android.view.View
import android.view.ViewGroup

/**
 * Created by zhaoxuzhang on 2018/2/7.
 */
object MeasureUtil {

    fun makeMeasureSpec(measureSpec: Int): Int {
        when (measureSpec) {
            ViewGroup.LayoutParams.WRAP_CONTENT ->
                return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), View.MeasureSpec.UNSPECIFIED)
            else ->
                return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), View.MeasureSpec.EXACTLY)
        }
    }

}