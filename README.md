# XPopupWindow, an enhanced PopupWindow using Kotlin

![Travis](https://img.shields.io/badge/build-passing-brightgreen.svg)

[中文版](https://github.com/XuDeveloper/XPopupWindow/blob/master/docs/README-ZH.md)

XPopupWindow is an open source library implemented in the Kotlin language, which further encapsulates and enhances the system's PopupWindow for ease of use.  It provides some extra useful features like flexibly setting the popup position, adjusting the popup animations and so on. This can help you create a Android popup more easily and reliable.

## Preview

<img src="https://raw.githubusercontent.com/xudeveloper/XPopupWindow/master/art/demo.gif" width="450" height="801" alt="XPopupWindow-demo"/>

## Features

* Create the custom popupWindow quickly and easily
* Set the position of the popupwindow in a more convenient way
* Adjust the popup's animations more freely

## Getting started

using Gradle：

```Groovy
allprojects {
    repositories {
        ...
	maven { url "https://jitpack.io" }
    }
}

dependencies {
    implementation 'com.github.XuDeveloper:XPopupWindow:1.0.1'
}
```


## Usage

```Kotlin
class TestPopupWindow(ctx: Context): XPopupWindow(ctx) {
    override fun getLayoutId(): Int {
        return R.layout.test
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.parent
    }

    override fun initViews() {
    }

    override fun initData() {
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
```

```Kotlin
private fun showPopup() {
   popupWindow = TestPopupWindow(this, 400, 300)    
   popupWindow?.showPopupFromScreenBottom(R.layout.activity_main)
}
```

There are lots of functions to set the popup location:

```Kotlin
fun showPopup(view: View, offsetX: Int, offsetY: Int, gravity: Int)
fun showPopupFromScreenBottom(layoutId: Int)
fun showPopupFromScreenTop(layoutId: Int)
fun showPopupFromScreenLeft(layoutId: Int)
fun showPopupFromScreenRight(layoutId: Int)
fun showPopupFromScreenCenter(layoutId: Int)
fun showPopupAtViewBottom(view: View, isShowFully: Boolean = false)
fun showPopupAtViewTop(view: View, isShowFully: Boolean = false)
fun showPopupAtViewLeft(view: View, isShowFully: Boolean = false)
fun showPopupAtViewRight(view: View, isShowFully: Boolean = false)
fun showPopupAtViewCenter(view: View)
```

You can set the dismiss listener!

```Kotlin
popupWindow?.setXPopupDismissListener(object : XPopupWindowDismissListener {
    override fun xPopupBeforeDismiss() {
    }

    override fun xPopupAfterDismiss() {
    }
})
```

If you want to change the background alpha, you can use these functions:

```Kotlin
fun setShowingBackgroundAlpha(alpha: Float)
fun setDismissBackgroundAlpha(alpha: Float)
```

If your custom popupwindow has a input box and you want to get its focus when the popupwindow shows, you can use this:

```kotlin
fun setAutoShowInput(inputView: View?, autoShowInput: Boolean)
```

* **you can checkout the xpopupwindowdemo for more usages!**


## Donation(Buy me a cup of lemon tea :smile:)

| 微信 |支付宝 | 
| ---- | ---- | 
| ![](https://github.com/XuDeveloper/XPopupWindow/blob/master/art/wechat.jpeg)      | ![](https://github.com/XuDeveloper/XPopupWindow/blob/master/art/alipay.jpeg) |

## Licenses

```license
Copyright [2018] XuDeveloper

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```