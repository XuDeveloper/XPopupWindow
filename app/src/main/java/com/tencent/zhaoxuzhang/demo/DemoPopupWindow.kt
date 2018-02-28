package com.tencent.zhaoxuzhang.demo

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button

/**
 * Created by zhaoxuzhang on 2018/2/6.
 */
class DemoPopupWindow : XPopupWindow, View.OnClickListener {
    var button1: Button? = null

    var button2: Button? = null

    var button3: Button? = null

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, w: Int, h: Int) : super(ctx, w, h)

    override fun getLayoutId(): Int {
        return R.layout.demo_popup_layout
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.demo_pop_layout_parent
    }

    override fun initViews(view: View) {
        button1 = view.findViewById(R.id.demo_bt_one) as Button
        button2 = view.findViewById(R.id.demo_bt_two) as Button
        button3 = view.findViewById(R.id.demo_bt_three) as Button

        button1!!.setOnClickListener(this)
        button2!!.setOnClickListener(this)
        button3!!.setOnClickListener(this)
    }

    override fun startAnim(view: View): ValueAnimator? {
        var curTranslationX = view.translationX
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        animator.duration = 5000
        return animator
    }

    override fun exitAnim(view: View): ValueAnimator {
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        animator.duration = 3000
        return animator
    }

    override fun animStyle(): Int {
        return 0
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.demo_bt_one -> Log.i("test", "1")
            R.id.demo_bt_two -> Log.i("test", "2")
            R.id.demo_bt_three -> Log.i("test", "3")
        }
    }

}