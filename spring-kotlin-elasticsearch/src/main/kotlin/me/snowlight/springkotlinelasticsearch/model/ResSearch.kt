package me.snowlight.springkotlinelasticsearch.model

data class ResSearch(
    val items: List<History>,
    val total: Long,
    val pageNext: List<Any>?,
)