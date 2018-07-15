package com.xu.xpopupwindowdemo.popup

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.Button
import com.xu.xpopupwindow.XPopupWindow
import com.xu.xpopupwindowdemo.R

/**
 * Created by Xu on 2018/6/17.
 * @author Xu
 */

class InputPopupWindow : XPopupWindow {
    val LOG = "InputPopupWindow"
    var btnLogin: Button? = null
    var etPhone: TextInputEditText? = null

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, w: Int, h: Int) : super(ctx, w, h)

    override fun getLayoutId(): Int {
        return R.layout.popup_input
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.input_parent
    }

    override fun initViews() {
        btnLogin = findViewById(R.id.btn_login)
        btnLogin?.setOnClickListener({ dismiss() })
        etPhone = findViewById(R.id.et_mobile)
    }

    override fun initData() {
        setShowingBackgroundAlpha(0.4f)
        setAutoShowInput(etPhone, true)
    }

    override fun startAnim(view: View): Animator? {
        var animatorX: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        var animatorY: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
        var set = AnimatorSet()
        set.play(animatorX).with(animatorY)
        set.duration = 500
        return set
    }

    override fun exitAnim(view: View): Animator? {
        var animatorX: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
        var animatorY: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f)
        var set = AnimatorSet()
        set.play(animatorX).with(animatorY)
        set.duration = 700
        return set
    }

    override fun animStyle(): Int {
        return -1
    }

}
