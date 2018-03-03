package com.tencent.zhaoxuzhang.demo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow
import com.tencent.zhaoxuzhang.xpopupwindow.`interface`.XPopupWindowDismissListener
import com.tencent.zhaoxuzhang.xpopupwindow.`interface`.XPopupWindowShowListener
import com.tencent.zhaoxuzhang.xpopupwindow.util.MeasureUtil
import com.tencent.zhaoxuzhang.xpopupwindow.util.ViewSpaceUtil

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

abstract class XPopupWindow : PopupWindow {

    private val TAG = "XPopupWindow"

    private lateinit var mCtx: Context

    private lateinit var mPopupView: View

    private lateinit var mInflater: LayoutInflater

    private var xPopupWindowShowListener: XPopupWindowShowListener? = null

    private var xPopupWindowDismissListener: XPopupWindowDismissListener? = null

    private var isUsingCustomAnim: Boolean = true
    private var isAnimRunning: Boolean = false

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

        width = w
        height = h

        // 测量宽高

        mPopupView.measure(MeasureUtil.makeMeasureSpec(width), MeasureUtil.makeMeasureSpec(height))

        contentView.measure(MeasureUtil.makeMeasureSpec(width), MeasureUtil.makeMeasureSpec(height))

        initViews(mPopupView)

        isFocusable = true
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable())

        // 设置动画
        if (animStyle() != -1) {
            animationStyle = animStyle()
            isUsingCustomAnim = false
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
        if (isUsingCustomAnim) {
            if (!isAnimRunning) {
                if (xPopupWindowDismissListener != null) {
                    xPopupWindowDismissListener!!.xPopupBeforeDismiss()
                }
                var animator: ValueAnimator? = exitAnim(contentView)
                if (animator != null) {
                    animator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            dismissXPopup()
                        }
                    })
                    animator.start()
                }
            }
        } else {
            dismissXPopup()
        }
    }

    private fun dismissXPopup() {
        super.dismiss()
        if (xPopupWindowDismissListener != null) {
            xPopupWindowDismissListener!!.xPopupAfterDismiss()
        }
    }

    // todo 加入标志位 animatorStyle 和 自定义Animation
    // todo isAnimRunning 当true onClick拦截（自定义onClick事件）

    private fun xPopupShowAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        if (xPopupWindowShowListener != null) {
            xPopupWindowShowListener!!.xPopupBeforeShow()
        }
        super.showAtLocation(parent, gravity, x, y)
        var animator: ValueAnimator? = startAnim(mPopupView)
        if (animator != null && isUsingCustomAnim) {
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    isAnimRunning = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    isAnimRunning = false
                    if (xPopupWindowShowListener != null) {
                        xPopupWindowShowListener!!.xPopupAfterShow()
                    }
                }
            })
            animator.start()
        }
    }

    private fun xPopupShowAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        if (xPopupWindowShowListener != null) {
            xPopupWindowShowListener!!.xPopupBeforeShow()
        }
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        var animator: ValueAnimator? = startAnim(mPopupView)
        if (animator != null && isUsingCustomAnim) {
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    isAnimRunning = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    isAnimRunning = false
                    if (xPopupWindowShowListener != null) {
                        xPopupWindowShowListener!!.xPopupAfterShow()
                    }
                }
            })
            animator.start();
        }
    }

    fun showPopup(view: View, offsetX: Int, offsetY: Int, gravity: Int) {
        xPopupShowAsDropDown(view, offsetX, offsetY, gravity)
    }

    fun showPopupFromScreenBottom(layoutId: Int) {
        xPopupShowAtLocation(mInflater.inflate(layoutId, null), Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
    }

    fun showPopupFromScreenTop(layoutId: Int) {
        xPopupShowAtLocation(mInflater.inflate(layoutId, null), Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
    }

    fun showPopupFromScreenLeft(layoutId: Int) {
        xPopupShowAtLocation(mInflater.inflate(layoutId, null), Gravity.LEFT or Gravity.CENTER_VERTICAL, 0, 0)
    }

    fun showPopupFromScreenRight(layoutId: Int) {
        xPopupShowAtLocation(mInflater.inflate(layoutId, null), Gravity.RIGHT or Gravity.CENTER_VERTICAL, 0, 0)
    }

    fun showPopupAtViewBottom(view: View, isShowFully: Boolean = false) {
        var offsetX = (view.width - contentView.measuredWidth) / 2
        var offsetY = 0
        if (isShowFully) {
            if (!ViewSpaceUtil.viewBottomSpace(mCtx, view, contentView)) {
                this.showPopupAtViewTop(view)
                return
            }
        }
        xPopupShowAsDropDown(view, offsetX, offsetY, Gravity.START)
    }

    fun showPopupAtViewTop(view: View, isShowFully: Boolean = false) {
        var offsetX = (view.width - contentView.measuredWidth) / 2
        var offsetY = -(contentView.measuredHeight + view.height)
        if (isShowFully) {
            if (!ViewSpaceUtil.viewTopSpace(view, contentView)) {
                this.showPopupAtViewBottom(view)
                return
            }
        }
        xPopupShowAsDropDown(view, offsetX, offsetY, Gravity.START)
    }

    fun showPopupAtViewLeft(view: View, isShowFully: Boolean = false) {
        var offsetX = -contentView.measuredWidth
        var offsetY = -(view.height + contentView.measuredHeight) / 2
        if (isShowFully) {
            if (!ViewSpaceUtil.viewleftSpace(view, contentView)) {
                this.showPopupAtViewRight(view)
                return
            }
        }
        xPopupShowAsDropDown(view, offsetX, offsetY, Gravity.START)
    }

    fun showPopupAtViewRight(view: View, isShowFully: Boolean = false) {
        var offsetX = view.measuredWidth
        var offsetY = -(view.height + contentView.measuredHeight) / 2
        if (isShowFully) {
            if (!ViewSpaceUtil.viewRightSpace(mCtx, view, contentView)) {
                this.showPopupAtViewLeft(view)
                return
            }
        }
        xPopupShowAsDropDown(view, offsetX, offsetY, Gravity.START)
    }

    fun getMeasuredWidth(): Int {
        return contentView.measuredWidth
    }

    fun getMeasuredHeight(): Int {
        return contentView.measuredHeight
    }

    fun setXPopupShowListener(listener: XPopupWindowShowListener) {
        xPopupWindowShowListener = listener
    }

    fun setXPopupDismissListener(listener: XPopupWindowDismissListener) {
        xPopupWindowDismissListener = listener
    }

    abstract fun getLayoutId(): Int

    abstract fun getLayoutParentNodeId(): Int // ??

    abstract fun initViews(view: View)

    abstract fun startAnim(view: View): ValueAnimator?

    abstract fun exitAnim(view: View): ValueAnimator?

    abstract fun animStyle(): Int

}