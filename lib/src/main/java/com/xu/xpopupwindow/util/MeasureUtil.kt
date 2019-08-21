package com.xu.xpopupwindow.util

import android.view.View
import android.view.ViewGroup

/**
 * Created by Xu on 2018/2/7.
 */
fun makeMeasureSpec(measureSpec: Int): Int = when (measureSpec) {
    ViewGroup.LayoutParams.WRAP_CONTENT ->
        View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), View.MeasureSpec.AT_MOST)
    else ->
        View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), View.MeasureSpec.EXACTLY)
}

