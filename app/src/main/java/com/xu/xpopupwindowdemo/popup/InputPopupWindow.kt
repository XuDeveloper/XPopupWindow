package com.xu.xpopupwindowdemo.popup

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import com.xu.xpopupwindow.XPopupWindow
import com.xu.xpopupwindow.listener.XPopupWindowDismissListener
import com.xu.xpopupwindowdemo.R

/**
 * Created by Xu on 2018/6/17.
 * @author Xu
 */
class InputPopupWindow: XPopupWindow, XPopupWindowDismissListener {
    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, w: Int, h: Int) : super(ctx, w, h) {
        setXPopupDismissListener(this)
        setShowingBackgroundAlpha(0.4f)
        autoShowInputMethod = true
    }

    override fun getLayoutId(): Int {
        return R.layout.popup_input
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.input_parent
    }

    override fun initViews(view: View) {
    }

    override fun startAnim(view: View): ValueAnimator? {
        var curTranslationX = view.translationX
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(view, "translationY", 700f, 0f)
        animator.duration = 1000
        return animator
    }

    override fun exitAnim(view: View): ValueAnimator? {
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0f, 700f)
        animator.duration = 1000
        return animator
    }

    override fun animStyle(): Int {
        return -1
    }

    override fun xPopupBeforeDismiss() {
    }

    override fun xPopupAfterDismiss() {
    }



}
