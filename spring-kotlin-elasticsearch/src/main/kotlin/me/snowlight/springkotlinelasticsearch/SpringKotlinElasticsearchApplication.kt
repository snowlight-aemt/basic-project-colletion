package me.snowlight.springkotlinelasticsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinElasticsearchApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinElasticsearchApplication>(*args)
}
