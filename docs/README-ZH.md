# XPopupWindow, 一个用Kotlin实现的增强版PopupWindow

![Travis](https://img.shields.io/badge/build-passing-brightgreen.svg)

[English Version](https://github.com/XuDeveloper/XPopupWindow/blob/master/docs/README-ZH.md)

XPopupWindow是一个用Kotlin语言实现的开源库，它对系统的PopupWindow进行进一步封装和加强以便于使用。它提供了许多额外的特性例如灵活设置弹窗位置，调整弹窗动画等等。这个库能帮助你更快创建一个稳定性高的Android PopupWindow。

## 预览



## 特性

* 简单快速地创建一个自定义弹窗
* 以一种相对便捷的方式设置弹窗位置
* 更加自由地调整你的弹窗动画


## 开始

使用Gradle：

```Groovy
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}

dependencies {
    compile ''
}
```