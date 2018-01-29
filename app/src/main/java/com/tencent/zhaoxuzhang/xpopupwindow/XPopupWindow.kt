package com.tencent.zhaoxuzhang.xpopupwindow

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

abstract class XPopupWindow(ctx: Context): PopupWindow(), View.OnClickListener {

    private val TAG = "XPopupWindow"

    private var mPopupView: View? = null

    constructor(ctx: Context, w: Int, h: Int) : this(ctx) {
        init(ctx, w, h)
    }

    private fun init(ctx: Context, w: Int, h: Int) {
        mPopupView = LayoutInflater.from(ctx).inflate(getLayoutId(), null)
        mPopupView!!.isFocusableInTouchMode = true

        contentView = mPopupView

        width = w
        height = h
        isFocusable = true
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable())

        setStartAnim()

        mPopupView!!.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            val height = mPopupView!!.findViewById<View>(getLayoutParentNodeId()).top
            val y = motionEvent.y
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss()
                }
            }
            true
        })

    }

    init {
        init(ctx, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        initViews()

    }

    override fun dismiss() {
        super.dismiss()
        setExitAnim()
    }

    abstract fun getLayoutId(): Int

    abstract fun getLayoutParentNodeId(): Int // ??

    abstract fun initViews()

    abstract fun setStartAnim()

    abstract fun setExitAnim()
}