package com.xu.xpopupwindowdemo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import com.xu.xpopupwindow.listener.XPopupWindowDismissListener
import com.xu.xpopupwindowdemo.popup.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnInputPopup: Button
    private lateinit var btnCommentPopup: Button
    private lateinit var btnCommentReversePopup: Button
    private lateinit var btnListPopup: Button
    private lateinit var btnMenuPopup: Button
    private lateinit var btnDialogPopup: Button

    private var inputPopupWindow: InputPopupWindow? = null
    private var commentPopupWindow: CommentPopupWindow? = null
    private var commentReversePopupWindow: CommentPopupWindow? = null
    private var listPopupWindow: ListPopupWindow? = null
    private var menuPopupWindow: MenuPopupWindow? = null
    private var dialogPopupWindow: DialogPopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInputPopup = findViewById(R.id.btn_input_popup)
        btnCommentPopup = findViewById(R.id.btn_comment_popup)
        btnCommentReversePopup = findViewById(R.id.btn_comment_reverse_popup)
        btnListPopup = findViewById(R.id.btn_list_popup)
        btnMenuPopup = findViewById(R.id.btn_menu_popup)
        btnDialogPopup = findViewById(R.id.btn_dialog_popup)

        btnInputPopup.setOnClickListener {
            showInputPopup()
        }

        btnCommentPopup.setOnClickListener {
            showCommentPopup()
        }

        btnCommentReversePopup.setOnClickListener {
            showCommentReversePopup()
        }

        btnListPopup.setOnClickListener {
            showListPopup()
        }

        btnMenuPopup.setOnClickListener {
            showMenuPopup()
        }

        btnDialogPopup.setOnClickListener {
            showDialogPopup()
        }
    }

    private fun showInputPopup() {
        inputPopupWindow = InputPopupWindow(this, 1000, 600)
        inputPopupWindow?.setXPopupDismissListener(object : XPopupWindowDismissListener {
            override fun xPopupBeforeDismiss() {
            }

            override fun xPopupAfterDismiss() {
                Snackbar.make(findViewById(android.R.id.content), "登录成功！", Snackbar.LENGTH_LONG).show()
            }
        })
        inputPopupWindow?.showPopupFromScreenCenter(R.layout.activity_main)
    }

    private fun showCommentPopup() {
        commentPopupWindow = CommentPopupWindow(this)
        commentPopupWindow?.showPopupAtViewRight(btnCommentPopup, true)
    }

    private fun showCommentReversePopup() {
        commentReversePopupWindow = CommentPopupWindow(this)
        commentReversePopupWindow?.showPopupAtViewLeft(btnCommentReversePopup, true)
//      也可调用 commentReversePopupWindow?.showPopupAtViewRight(btnCommentReversePopup, true)
    }

    private fun showListPopup() {
        listPopupWindow = ListPopupWindow(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        listPopupWindow?.showPopupFromScreenBottom(R.layout.activity_main)
    }

    private fun showMenuPopup() {
        menuPopupWindow = MenuPopupWindow(this, 400, 300)
        menuPopupWindow?.showPopupAtViewCenter(btnMenuPopup)
    }

    private fun showDialogPopup() {
        dialogPopupWindow = DialogPopupWindow(this)
        dialogPopupWindow?.showPopupFromScreenCenter(R.layout.activity_main)
    }

}
