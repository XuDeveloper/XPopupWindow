package com.tencent.zhaoxuzhang.xpopupwindow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

abstract class XPopupWindow(ctx: Context): PopupWindow(ctx) {

    private val mPopupView: View?

    init {
        mPopupView = LayoutInflater.from(ctx).inflate(getLayoutId(), null)
        initViews()

    }

    abstract fun getLayoutId(): Int

    abstract fun initViews()

    abstract fun setStartAnim()

    abstract fun setExitAnim()
}