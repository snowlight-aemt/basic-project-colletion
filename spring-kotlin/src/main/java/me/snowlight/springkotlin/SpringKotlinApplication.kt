package me.snowlight.springkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SpringKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinApplication>()
}