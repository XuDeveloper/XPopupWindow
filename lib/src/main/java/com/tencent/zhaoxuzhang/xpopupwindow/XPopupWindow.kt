package com.tencent.zhaoxuzhang.demo

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow
import com.tencent.zhaoxuzhang.xpopupwindow.util.MeasureUtil

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

abstract class XPopupWindow : PopupWindow {

    private val TAG = "XPopupWindow"

    private lateinit var mCtx: Context

    private lateinit var mPopupView: View

    private lateinit var mInflater: LayoutInflater


    constructor(ctx: Context) {
        init(ctx, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    constructor(ctx: Context, w: Int, h: Int) : this(ctx) {
        init(ctx, w, h)
    }

    private fun init(ctx: Context, w: Int, h: Int) {
        mCtx = ctx
        mInflater = LayoutInflater.from(ctx)

        mPopupView = mInflater.inflate(getLayoutId(), null)
        mPopupView.isFocusableInTouchMode = true

        contentView = mPopupView

        // 测量宽高
        contentView.measure(MeasureUtil.makeMeasureSpec(width), MeasureUtil.makeMeasureSpec(height))

        initViews(mPopupView)

        width = w
        height = h
        isFocusable = true
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable())

        if (startAnim() != 0) {
            animationStyle = startAnim()
        }

        mPopupView.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            val height = mPopupView.findViewById<View>(getLayoutParentNodeId()).top
            val y = motionEvent.y
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss()
                }
            }
            true
        })

    }

    override fun dismiss() {
        super.dismiss()
        exitAnim()
    }

    fun showPopupFromScreenBottom(layoutId: Int) {
        showAtLocation(mInflater.inflate(layoutId, null), Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
    }

    fun showPopupFromScreenTop(layoutId: Int) {
        showAtLocation(mInflater.inflate(layoutId, null), Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
    }

    fun showPopupFromScreenLeft(layoutId: Int) {
        showAtLocation(mInflater.inflate(layoutId, null), Gravity.LEFT or Gravity.CENTER_VERTICAL, 0, 0)
    }

    fun showPopupFromScreenRight(layoutId: Int) {
        showAtLocation(mInflater.inflate(layoutId, null), Gravity.RIGHT or Gravity.CENTER_VERTICAL, 0, 0)
    }

    fun showPopupAtViewBottom(view: View) {
        var offsetX = Math.abs(contentView.measuredWidth - view.width) / 2
        var offsetY = 0
        showAsDropDown(view, offsetX, offsetY, Gravity.START)
    }


    abstract fun getLayoutId(): Int

    abstract fun getLayoutParentNodeId(): Int // ??

    abstract fun initViews(view: View)

    abstract fun startAnim(): Int

    abstract fun exitAnim()

}