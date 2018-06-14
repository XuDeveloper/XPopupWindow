package com.xu.xpopupwindowdemo

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import com.xu.xpopupwindow.XPopupWindow
import com.xu.xpopupwindow.listener.XPopupWindowDismissListener


/**
 * Created by Xu on 2018/2/6.
 */
class DemoPopupWindow : XPopupWindow, View.OnClickListener, XPopupWindowDismissListener {
    override fun xPopupAfterDismiss() {
        Log.i("test", "after")
    }

    override fun xPopupBeforeDismiss() {
        Log.i("test", "before")
    }

    var button1: Button? = null

    var button2: Button? = null

    var button3: Button? = null

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, w: Int, h: Int) : super(ctx, w, h) {
        setXPopupDismissListener(this)
        setShowingBackgroundAlpha(0.4f)
        autoShowInputMethod = true
    }

    override fun getLayoutId(): Int {
        return R.layout.demo_popup_layout
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.demo_pop_layout_parent
    }

    override fun initViews(view: View) {
        button1 = view.findViewById<Button>(R.id.demo_bt_one)
        button2 = view.findViewById<Button>(R.id.demo_bt_two)
        button3 = view.findViewById<Button>(R.id.demo_bt_three)

        button1!!.setOnClickListener(this)
        button2!!.setOnClickListener(this)
        button3!!.setOnClickListener(this)
    }

    override fun startAnim(view: View): ValueAnimator? {
        var curTranslationX = view.translationX
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(view, "translationX", -200f, 0f)
        animator.duration = 2500
        return animator
    }

    override fun exitAnim(view: View): ValueAnimator {
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        animator.duration = 1000
        return animator
    }

    override fun animStyle(): Int {
        return -1
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.demo_bt_one -> Log.i("test", "1")
            R.id.demo_bt_two -> Log.i("test", "2")
            R.id.demo_bt_three -> Log.i("test", "3")
        }
    }

}