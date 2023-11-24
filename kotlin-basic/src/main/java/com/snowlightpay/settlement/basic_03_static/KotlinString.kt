package com.snowlightpay.settlement.basic_03_static

import kotlin.reflect.typeOf

fun String.first(): Char {
    return this[0]
}

fun String.addFirst(char: Char): String {
    return char + this.substring(0)
}

fun main() {
    val first = "Swap".first()
//    println(first is String)
    println(first is Char)

    val addFirst = "Swap".addFirst('A')
    println(addFirst is String)
    println(addFirst)
}