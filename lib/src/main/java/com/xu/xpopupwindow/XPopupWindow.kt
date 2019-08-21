package com.xu.xpopupwindow

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow
//import com.xu.xpopupwindow.config.XPopupConfig
import com.xu.xpopupwindow.listener.XPopupWindowDismissListener
import com.xu.xpopupwindow.listener.XPopupWindowShowListener
import com.xu.xpopupwindow.util.*

/**
 * Created by Xu on 2018/1/26.
 */

abstract class XPopupWindow : PopupWindow {

    val TAG = "XPopupWindow"

    lateinit var mCtx: Context
    private lateinit var mPopupView: View
    private lateinit var mInflater: LayoutInflater

    private var inputView: View? = null

    private var xPopupWindowShowListener: XPopupWindowShowListener? = null
    private var xPopupWindowDismissListener: XPopupWindowDismissListener? = null

    private var isUsingCustomAnim: Boolean = false
    private var isAnimRunning: Boolean = false
    private var autoShowInput: Boolean = false

//    private var config: XPopupConfig? = null

    private var stBackgroundAlpha: Float = 1f
    private var endBackgroundAlpha: Float = 1f
    private var isChangingBackgroundAlpha: Boolean = false

    constructor(ctx: Context) : super(ctx) {
        init(ctx, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
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

        if (w != LayoutParams.WRAP_CONTENT) {
            width = w
        }
        if (h != LayoutParams.WRAP_CONTENT) {
            height = h
        }

        // 测量宽高
        mPopupView.measure(makeMeasureSpec(width), makeMeasureSpec(height))

        contentView.measure(makeMeasureSpec(width), makeMeasureSpec(height))

        initViews()

        initData()

        setBackgroundDrawable(ColorDrawable())
        isFocusable = true
        isOutsideTouchable = true
        isTouchable = true

        if (animStyle() != -1) {
            animationStyle = animStyle()
        }

        if (startAnim(contentView) != null || exitAnim(contentView) != null) {
            isUsingCustomAnim = true
        }

        this.setTouchInterceptor { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                if ((motionEvent.x < 0 || motionEvent.x > view.width) ||
                        (motionEvent.y < 0 || motionEvent.y > view.height)) {
                    dismiss()
                    true
                }
            }
            false
        }

        softInputMode = if (autoShowInput) {
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        } else {
            WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
        }
    }

    override fun dismiss() {
        if (inputView != null) {
            hideInputMethod(inputView)
        }
        if (isUsingCustomAnim && !isAnimRunning) {
            xPopupWindowDismissListener?.xPopupBeforeDismiss()
            val animator: Animator? = exitAnim(contentView)
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
        val animator: Animator? = startAnim(mPopupView)
        if (isUsingCustomAnim) {
            animator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    isAnimRunning = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    isAnimRunning = false
                    xPopupWindowShowListener?.xPopupAfterShow()
                    setBackgroundAlpha(stBackgroundAlpha)
                    if (autoShowInput && inputView != null) {
                        inputView?.requestFocus()
                        showInputMethod(inputView, 300)
                    }
                }
            })
            animator?.start()
        } else {
            xPopupWindowShowListener?.xPopupAfterShow()
            setBackgroundAlpha(stBackgroundAlpha)
            if (autoShowInput && inputView != null) {
                inputView?.requestFocus()
                showInputMethod(inputView, 300)
            }
        }
    }

    private fun xPopupShowAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        xPopupWindowShowListener?.xPopupBeforeShow()
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        val animator: Animator? = startAnim(mPopupView)
        if (isUsingCustomAnim) {
            animator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    isAnimRunning = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    isAnimRunning = false
                    xPopupWindowShowListener?.xPopupAfterShow()
                    setBackgroundAlpha(stBackgroundAlpha)
                    if (autoShowInput && inputView != null) {
                        inputView?.requestFocus()
                        showInputMethod(inputView, 300)
                    }
                }
            })
            animator?.start()
        } else {
            xPopupWindowShowListener?.xPopupAfterShow()
            setBackgroundAlpha(stBackgroundAlpha)
            if (autoShowInput && inputView != null) {
                inputView?.requestFocus()
                showInputMethod(inputView, 300)
            }
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

    fun showPopupFromScreenCenter(layoutId: Int) {
        xPopupShowAtLocation(mInflater.inflate(layoutId, null), Gravity.CENTER, 0, 0)
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

    fun showPopupAtViewCenter(view: View) {
        val offsetX = view.width / 2
        val offsetY = (view.height - contentView.measuredHeight) / 2
        xPopupShowAsDropDown(view, offsetX, offsetY, Gravity.START)
    }

    fun setAutoShowInput(inputView: View?, autoShowInput: Boolean) {
        this.inputView = inputView
        this.autoShowInput = autoShowInput
    }

    fun getPopupView(): View {
        return mPopupView
    }

    fun getContext(): Context {
        return mCtx
    }

    fun <T : View> findViewById(id: Int): T? {
        if (id != 0) {
            return mPopupView.findViewById(id)
        }
        return null
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

    abstract fun initViews()

    abstract fun initData()

    abstract fun startAnim(view: View): Animator?

    abstract fun exitAnim(view: View): Animator?

    abstract fun animStyle(): Int

}