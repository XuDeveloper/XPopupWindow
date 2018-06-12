package com.tencent.Xu.xpopupwindow.util

import android.view.View
import android.view.ViewGroup

/**
 * Created by Xu on 2018/2/7.
 */
object MeasureUtil {

    fun makeMeasureSpec(measureSpec: Int): Int = when (measureSpec) {
        ViewGroup.LayoutParams.WRAP_CONTENT ->
            View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), View.MeasureSpec.UNSPECIFIED)
        else ->
            View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), View.MeasureSpec.EXACTLY)
    }

}