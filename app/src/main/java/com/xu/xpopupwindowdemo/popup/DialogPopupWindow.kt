package com.xu.xpopupwindowdemo.popup

import android.animation.Animator
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.xu.xpopupwindow.XPopupWindow
import com.xu.xpopupwindowdemo.R

/**
 * Created by Xu on 2018/6/18.
 * @author Xu
 */
class DialogPopupWindow : XPopupWindow, View.OnClickListener {

    var tvSure: TextView? = null
    var tvCancel: TextView? = null

    constructor(ctx: Context) : super(ctx)

    override fun getLayoutId(): Int {
        return R.layout.popup_dialog
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.dialog_parent
    }

    override fun initViews() {
        setShowingBackgroundAlpha(0.2f)
        tvSure = findViewById(R.id.tv_dialog_sure)
        tvCancel = findViewById(R.id.tv_dialog_cancel)
        tvSure?.setOnClickListener(this)
        tvCancel?.setOnClickListener(this)
    }

    override fun startAnim(view: View): Animator? {
        return null
    }

    override fun exitAnim(view: View): Animator? {
        return null
    }

    override fun animStyle(): Int {
        return R.style.DialogPopupAnim
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_dialog_sure -> Log.i(TAG, "sure")
            R.id.tv_dialog_cancel -> Log.i(TAG, "cancel")
            else -> Unit
        }
    }

}