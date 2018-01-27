package com.tencent.zhaoxuzhang.xpopupwindow

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

abstract class XPopupWindow(ctx: Context) {

    private val TAG = "XPopupWindow"

    //元素定义
    protected var mPopupWindow: PopupWindow? = null

    protected var mPopupView: View? = null

    constructor(ctx: Context, w: Int, h: Int): this(ctx) {
        init(ctx, w, h)
    }

    fun init(ctx: Context, w: Int, h: Int) {
        mPopupView = LayoutInflater.from(ctx).inflate(getLayoutId(), null)
        mPopupView!!.isFocusableInTouchMode = true

        mPopupWindow = PopupWindow(ctx)
        mPopupWindow!!.contentView = mPopupView

        mPopupWindow!!.width = w
        mPopupWindow!!.height = h
        mPopupWindow!!.isFocusable = true
        mPopupWindow!!.isOutsideTouchable = true
        mPopupWindow!!.setBackgroundDrawable(ColorDrawable())


    }

    init {
        init(ctx, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        initViews()

    }

    abstract fun getLayoutId(): Int

    abstract fun initViews()

    abstract fun setStartAnim()

    abstract fun setExitAnim()
}