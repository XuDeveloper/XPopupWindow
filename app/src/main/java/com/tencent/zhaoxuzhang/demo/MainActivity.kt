package com.tencent.zhaoxuzhang.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var popupWindow: DemoPopupWindow? = null
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById<Button>(R.id.button)

        button.setOnClickListener(View.OnClickListener { v ->
            popupWindow = DemoPopupWindow(this, 500, 300)
            popupWindow!!.showPopupFromViewBottom(button)
        })
    }
}
