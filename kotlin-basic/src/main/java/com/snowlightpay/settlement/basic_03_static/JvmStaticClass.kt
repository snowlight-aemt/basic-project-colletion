package com.snowlightpay.settlement.basic_03_static

class JvmStaticClass {
    companion object {
        @JvmStatic
        fun hello() = "hello!"
    }
}

object JvmStaticHiObject {
    @JvmStatic
    fun hi() = "Hi"
}

fun main () {
    val hello = JvmStaticClass.hello()
    val hi = JvmStaticHiObject.hi()
}