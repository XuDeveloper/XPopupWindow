package com.xu.xpopupwindowdemo.popup

import android.animation.Animator
import android.content.Context
import android.view.View
import com.xu.xpopupwindow.XPopupWindow
import com.xu.xpopupwindowdemo.R

/**
 * Created by Xu on 2018/6/18.
 * @author Xu
 */
class CommentPopupWindow: XPopupWindow {

    constructor(ctx: Context): super(ctx)

    constructor(ctx: Context, w: Int, h: Int) : super(ctx, w, h) {
        autoShowInputMethod = true
    }

    override fun getLayoutId(): Int {
        return R.layout.popup_comment
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.comment_parent
    }

    override fun initViews(view: View) {

    }

    override fun startAnim(view: View): Animator? {
        return null
    }

    override fun exitAnim(view: View): Animator? {
        return null
    }

    override fun animStyle(): Int {
        return -1
    }

}