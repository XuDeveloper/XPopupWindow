package com.xu.xpopupwindow

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow
import com.xu.xpopupwindow.listener.XPopupWindowDismissListener
import com.xu.xpopupwindow.listener.XPopupWindowShowListener
import com.xu.xpopupwindow.util.MeasureUtil

/**
 * Created by Xu on 2018/1/26.
 */

abstract class XPopupWindow : PopupWindow {

    val TAG = "XPopupWindow"

    private lateinit var mCtx: Context

    private lateinit var mPopupView: View

    private lateinit var mInflater: LayoutInflater

    private var xPopupWindowShowListener: XPopupWindowShowListener? = null
    private var xPopupWindowDismissListener: XPopupWindowDismissListener? = null

    private var isUsingCustomAnim: Boolean = true
    private var isAnimRunning: Boolean = false

    private var stBackgroundAlpha: Float = 1f
    private var endBackgroundAlpha: Float = 1f
    private var isChangingBackgroundAlpha: Boolean = false

    var autoShowInputMethod: Boolean = false

    constructor(ctx: Context) : super(ctx) {
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

        if (animStyle() != -1) {
            animationStyle = animStyle()
            isUsingCustomAnim = false
        }

        mPopupView.setOnTouchListener({ _, motionEvent ->
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
        if (isUsingCustomAnim && !isAnimRunning) {
            xPopupWindowDismissListener?.xPopupBeforeDismiss()
            val animator: ValueAnimator? = exitAnim(contentView)
            animator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    dismissXPopup()
                }
            })
            animator?.start()
        } else {
            dismissXPopup()
        }
    }

    private fun dismissXPopup() {
        super.dismiss()
        xPopupWindowDismissListener?.xPopupAfterDismiss()
        setBackgroundAlpha(endBackgroundAlpha)
    }

    private fun xPopupShowAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        xPopupWindowShowListener?.xPopupBeforeShow()
        super.showAtLocation(parent, gravity, x, y)
        val animator: ValueAnimator? = startAnim(mPopupView)
        if (isUsingCustomAnim) {
            animator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    isAnimRunning = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    isAnimRunning = false
                    if (xPopupWindowShowListener != null) {
                        xPopupWindowShowListener!!.xPopupAfterShow()
                    }
                    setBackgroundAlpha(stBackgroundAlpha)
                }
            })
            animator?.start()
        } else {
            setBackgroundAlpha(stBackgroundAlpha)
        }
    }

    private fun xPopupShowAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        xPopupWindowShowListener?.xPopupBeforeShow()
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        val animator: ValueAnimator? = startAnim(mPopupView)
        if (isUsingCustomAnim) {
            animator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    isAnimRunning = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    isAnimRunning = false
                    if (xPopupWindowShowListener != null) {
                        xPopupWindowShowListener!!.xPopupAfterShow()
                    }
                    setBackgroundAlpha(stBackgroundAlpha)
                }
            })
            animator?.start()
        } else {
            setBackgroundAlpha(stBackgroundAlpha)
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
        xPopupShowAtLocation(mInflater.inflate(layoutId, null), Gravity.START or Gravity.CENTER_VERTICAL, 0, 0)
    }

    fun showPopupFromScreenRight(layoutId: Int) {
        xPopupShowAtLocation(mInflater.inflate(layoutId, null), Gravity.END or Gravity.CENTER_VERTICAL, 0, 0)
    }

    fun showPopupAtViewBottom(view: View, isShowFully: Boolean = false) {
        val offsetX = (view.width - contentView.measuredWidth) / 2
        val offsetY = 0
        if (isShowFully) {
            if (!view.hasBottomSpace(mCtx, contentView)) {
                showPopupAtViewTop(view)
                return
            }
        }
        xPopupShowAsDropDown(view, offsetX, offsetY, Gravity.START)
    }

    fun showPopupAtViewTop(view: View, isShowFully: Boolean = false) {
        val offsetX = (view.width - contentView.measuredWidth) / 2
        val offsetY = -(contentView.measuredHeight + view.height)
        if (isShowFully) {
            if (!view.hasTopSpace(contentView)) {
                showPopupAtViewBottom(view)
                return
            }
        }
        xPopupShowAsDropDown(view, offsetX, offsetY, Gravity.START)
    }

    fun showPopupAtViewLeft(view: View, isShowFully: Boolean = false) {
        val offsetX = -contentView.measuredWidth
        val offsetY = -(view.height + contentView.measuredHeight) / 2
        if (isShowFully) {
            if (!view.hasLeftSpace(contentView)) {
                showPopupAtViewRight(view)
                return
            }
        }
        xPopupShowAsDropDown(view, offsetX, offsetY, Gravity.START)
    }

    fun showPopupAtViewRight(view: View, isShowFully: Boolean = false) {
        val offsetX = view.measuredWidth
        val offsetY = -(view.height + contentView.measuredHeight) / 2
        if (isShowFully) {
            if (!view.hasRightSpace(mCtx, contentView)) {
                showPopupAtViewLeft(view)
                return
            }
        }
        xPopupShowAsDropDown(view, offsetX, offsetY, Gravity.START)
    }

    fun setXPopupShowListener(listener: XPopupWindowShowListener) {
        xPopupWindowShowListener = listener
    }

    fun setXPopupDismissListener(listener: XPopupWindowDismissListener) {
        xPopupWindowDismissListener = listener
    }

    fun setShowingBackgroundAlpha(alpha: Float) {
        isChangingBackgroundAlpha = true
        stBackgroundAlpha = alpha
    }

    fun setDismissBackgroundAlpha(alpha: Float) {
        isChangingBackgroundAlpha = true
        endBackgroundAlpha = alpha
    }

    private fun setBackgroundAlpha(alpha: Float) {
        if (mCtx !is Activity) {
            throw Exception("context is not activity!")
        }
        if (!isChangingBackgroundAlpha) {
            return
        }
        val activity: Activity = mCtx as Activity
        val layoutParams: WindowManager.LayoutParams = activity.window.attributes
        layoutParams.alpha = alpha
        // 解决华为手机背景变暗无效问题
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        activity.window.attributes = layoutParams
    }

    abstract fun getLayoutId(): Int

    abstract fun getLayoutParentNodeId(): Int

    abstract fun initViews(view: View)

    abstract fun startAnim(view: View): ValueAnimator?

    abstract fun exitAnim(view: View): ValueAnimator?

    abstract fun animStyle(): Int

}