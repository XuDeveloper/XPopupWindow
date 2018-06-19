package com.xu.xpopupwindowdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.xu.xpopupwindowdemo.popup.CommentPopupWindow
import com.xu.xpopupwindowdemo.popup.InputPopupWindow

class MainActivity : AppCompatActivity() {

    private var inputPopupWindow: InputPopupWindow? = null
    private var commentPopupWindow: CommentPopupWindow? = null
    lateinit var btnInputPopup: Button
    lateinit var btnCommentPopup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInputPopup = findViewById(R.id.bt_input_popup)
        btnCommentPopup = findViewById(R.id.bt_comment_popup)

        btnInputPopup.setOnClickListener({
            inputPopupWindow = InputPopupWindow(this, 1000, 700)
            inputPopupWindow?.showPopupFromScreenCenter(R.layout.activity_main)
        })

        btnCommentPopup.setOnClickListener({
            commentPopupWindow = CommentPopupWindow(this, btnCommentPopup)
            commentPopupWindow?.showPopupAtViewRight(btnCommentPopup, true)
        })
    }
}
