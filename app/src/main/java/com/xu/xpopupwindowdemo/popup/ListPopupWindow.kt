package com.xu.xpopupwindowdemo.popup

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.xu.xpopupwindow.XPopupWindow
import com.xu.xpopupwindowdemo.R

/**
 * Created by Xu on 2018/6/17.
 * @author Xu
 */

class ListPopupWindow : XPopupWindow {
    val LOG = "ListPopupWindow"
    var rv: RecyclerView? = null

    constructor(ctx: Context) : super(ctx)

    override fun getLayoutId(): Int {
        return R.layout.popup_list
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.list_parent
    }

    override fun initViews(view: View) {
        rv = findViewById(R.id.rv_list)
    }

    override fun startAnim(view: View): Animator? {
        var animatorX: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        var animatorY: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
        var set = AnimatorSet()
        set.play(animatorX).with(animatorY)
        set.duration = 1000
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

    class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        constructor(list: List<String>) {

        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        }

        override fun getItemCount(): Int {
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    }

}
