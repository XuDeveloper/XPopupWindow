package com.tencent.zhaoxuzhang.xpopupwindow.util

import android.content.Context
import android.view.View

/**
 * Created by Xu on 2018/2/14.
 */
object ViewSpaceUtil {

    fun viewleftSpace(view: View, popupContentView: View): Boolean {
        var viewLoc = ViewLocationUtil.getViewLocationArr(view)
        if (viewLoc[0] <= popupContentView.measuredWidth) {
            return false
        }
        return true
    }

    fun viewRightSpace(mCtx: Context, view: View, popupContentView: View): Boolean {
        var viewLoc = ViewLocationUtil.getViewLocationArr(view)
        var test = ScreenUtil.getScreenWidth(mCtx) - viewLoc[0] - view.width
        if (ScreenUtil.getScreenWidth(mCtx) - viewLoc[0] - view.width <= popupContentView.measuredWidth) {
            return false
        }
        return true
    }

    fun viewTopSpace(view: View, popupContentView: View): Boolean {
        var viewLoc = ViewLocationUtil.getViewLocationArr(view)
        if (viewLoc[1] <= popupContentView.measuredHeight) {
            return false
        }
        return true
    }

    fun viewBottomSpace(mCtx: Context, view: View, popupContentView: View): Boolean {
        var viewLoc = ViewLocationUtil.getViewLocationArr(view)
        if (ScreenUtil.getScreenHeight(mCtx) - viewLoc[1] - view.height <= popupContentView.measuredHeight) {
            return false
        }
        return true
    }


}