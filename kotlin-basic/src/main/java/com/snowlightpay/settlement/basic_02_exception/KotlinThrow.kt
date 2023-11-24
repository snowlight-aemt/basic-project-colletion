package com.snowlightpay.settlement.basic_02_exception

import java.io.IOException

class KotlinThrow {

    @Throws(IOException::class)
    fun throwIOException() {
        throw IOException("체크드 익셉션인 IOException 발생")
    }
}


fun main() {
    // Kotlin 에 예외에는 체크되 예외가 존재하지 않는다. 따라서 아래에 자바에 예외를 처리하지 않아도 된다.
    val javaThrow = JavaThrow()
    javaThrow.throwIoException()

    val kotlinThrow = KotlinThrow()
    kotlinThrow.throwIOException()
}