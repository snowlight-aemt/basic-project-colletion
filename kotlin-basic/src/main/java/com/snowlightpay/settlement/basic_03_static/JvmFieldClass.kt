package com.snowlightpay.settlement.basic_03_static

class JvmFieldClass {
    companion object {
        val id = 1234
        const val CODE = 1234
    }
}

object JvmFieldObject {
    val name = "tony"
    const val FAMILY = "tony"
}

fun main () {
    val id = JvmFieldClass.id
    val name = JvmFieldObject.name
}