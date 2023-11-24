package com.snowlightpay.settlement.basic_03_static

class HelloClass {
    companion object {
        fun hello() = "hello!"
    }
}

object HiObject {
    fun hi() = "Hi"
}

fun main() {
    val hello = HelloClass.hello()
    HiObject.hi()
}

