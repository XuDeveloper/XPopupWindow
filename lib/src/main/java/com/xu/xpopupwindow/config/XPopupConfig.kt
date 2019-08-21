package com.xu.xpopupwindow.config

/**
 * Created by xu on 2018/9/4.
 */
data class Student(val name: String, val age: Int)

fun main(args: Array<String>) {
    val student = Student("David", 12)
    println(student)
}
