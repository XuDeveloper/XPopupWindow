package com.xu.xpopupwindow

import android.content.Context
import android.view.View
import com.xu.xpopupwindow.util.getScreenHeight
import com.xu.xpopupwindow.util.getScreenWidth

/**
 * Created by Xu on 2018/6/12.
 *
 * @author Xu
 */

fun View.getViewLocationArr(): IntArray {
    var viewLoc = intArrayOf(0, 0)
    getLocationOnScreen(viewLoc)
    return viewLoc
}

fun View.hasLeftSpace(popupContentView: View): Boolean {
    var viewLoc = getViewLocationArr()
    if (viewLoc[0] <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasRightSpace(mCtx: Context,  popupContentView: View): Boolean {
    var viewLoc = getViewLocationArr()
    if (getScreenWidth(mCtx) - viewLoc[0] - width <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasTopSpace(popupContentView: View): Boolean {
    var viewLoc = getViewLocationArr()
    if (viewLoc[1] <= popupContentView.measuredHeight) {
        return false
    }
    return true
}

fun View.hasBottomSpace(mCtx: Context,  popupContentView: View): Boolean {
    var viewLoc = getViewLocationArr()
    if (getScreenHeight(mCtx) - viewLoc[1] - height <= popupContentView.measuredHeight) {
        return false
    }
    return true
}



