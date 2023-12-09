package me.snowlight.springkotlinstomp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinStompApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinStompApplication>(*args)
}
