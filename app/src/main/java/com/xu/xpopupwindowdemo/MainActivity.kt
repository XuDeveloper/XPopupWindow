package com.xu.xpopupwindowdemo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.xu.xpopupwindow.listener.XPopupWindowDismissListener
import com.xu.xpopupwindowdemo.popup.CommentPopupWindow
import com.xu.xpopupwindowdemo.popup.InputPopupWindow

class MainActivity : AppCompatActivity() {

    private lateinit var btnInputPopup: Button
    private lateinit var btnCommentPopup: Button
    private lateinit var btnCommentReversePopup: Button

    private var inputPopupWindow: InputPopupWindow? = null
    private var commentPopupWindow: CommentPopupWindow? = null
    private var commentReversePopupWindow: CommentPopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInputPopup = findViewById(R.id.btn_input_popup)
        btnCommentPopup = findViewById(R.id.btn_comment_popup)
        btnCommentReversePopup = findViewById(R.id.btn_comment_reverse_popup)

        btnInputPopup.setOnClickListener({
            showInputPopup()
        })

        btnCommentPopup.setOnClickListener({
            showCommentPopup()
        })

        btnCommentReversePopup.setOnClickListener {
            showCommentReversePopup()
        }
    }

    private fun showInputPopup() {
        inputPopupWindow = InputPopupWindow(this, 1000, 700)
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
        commentPopupWindow = CommentPopupWindow(this, btnCommentPopup)
        commentPopupWindow?.showPopupAtViewRight(btnCommentPopup, true)
    }

    private fun showCommentReversePopup() {
        commentReversePopupWindow = CommentPopupWindow(this, btnCommentReversePopup)
        commentReversePopupWindow?.showPopupAtViewLeft(btnCommentReversePopup, true)
//      也可调用 commentReversePopupWindow?.showPopupAtViewRight(btnCommentReversePopup, true)
    }



}
