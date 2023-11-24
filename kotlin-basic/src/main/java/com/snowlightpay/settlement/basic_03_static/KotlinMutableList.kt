package com.snowlightpay.settlement.basic_03_static

fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1]  = this[index2]
    this[index2] = tmp
}

fun main() {
    val list = mutableListOf(1, 2, 3)
    list.swap(0 ,2)

    list.forEach {i ->
        println("${i}")
    }
}
