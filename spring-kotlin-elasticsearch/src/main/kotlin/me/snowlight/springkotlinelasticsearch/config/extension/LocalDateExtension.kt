package me.snowlight.springkotlinelasticsearch.config.extension

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDate(format: String = "yyyyMMdd"): LocalDate {
    return LocalDate.parse(this.filter { it.isDigit() }, DateTimeFormatter.ofPattern(format))
}

fun LocalDate.toString(format: String): String {
    return this.format(DateTimeFormatter.ofPattern(format))
}