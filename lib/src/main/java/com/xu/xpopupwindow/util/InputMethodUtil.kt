package com.xu.xpopupwindow.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Xu on 2018/3/8.
 */
fun showInputMethod(view: View?) {
    val imm: InputMethodManager = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun showInputMethod(view: View?, delay: Long) {
    view?.postDelayed({
        showInputMethod(view)
    }, delay)
}

fun hideInputMethod(view: View?) {
    val imm: InputMethodManager = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

