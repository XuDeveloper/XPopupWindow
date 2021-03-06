package com.xu.xpopupwindowdemo.popup

import android.animation.Animator
import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xu.xpopupwindow.XPopupWindow
import com.xu.xpopupwindowdemo.R

/**
 * Created by Xu on 2018/6/17.
 * @author Xu
 */

class ListPopupWindow : XPopupWindow {

    val LOG = "ListPopupWindow"
    private var rv: RecyclerView? = null
    private var adapter: CustomAdapter? = null
    private var list: List<String> = emptyList()
    private var manager: LinearLayoutManager? = null

    constructor(ctx: Context, w: Int, h: Int) : super(ctx, w, h)

    override fun getLayoutId(): Int {
        return R.layout.popup_list
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.list_parent
    }

    override fun initViews() {
        rv = findViewById(R.id.rv_list)
    }

    override fun initData() {
        list = mCtx.resources.getStringArray(R.array.nba_teams).asList()
        adapter = CustomAdapter(list, getContext())
        manager = LinearLayoutManager(getContext())
        rv?.addItemDecoration(DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL))
        rv?.setHasFixedSize(true)
        rv?.layoutManager = manager
        rv?.itemAnimator = DefaultItemAnimator()
        rv?.adapter = adapter
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

    class CustomAdapter(private val list: List<String>, private var context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_item_list,
                        parent, false))

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvItem?.text = list[position]
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvItem: TextView? = null

            init {
                tvItem = itemView.findViewById(R.id.rv_item_list_text)
            }
        }
    }

}
