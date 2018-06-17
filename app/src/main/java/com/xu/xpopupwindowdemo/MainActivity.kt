package com.xu.xpopupwindowdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.xu.xpopupwindowdemo.popup.InputPopupWindow

class MainActivity : AppCompatActivity() {

    var popupWindow: InputPopupWindow? = null
    lateinit var btnInputPopup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInputPopup = findViewById(R.id.bt_input_popup)

        btnInputPopup.setOnClickListener(View.OnClickListener { v ->
            popupWindow = InputPopupWindow(this, 1000, 700)
            popupWindow?.showPopupFromScreenBottom(R.layout.activity_main)
        })

    }
}
