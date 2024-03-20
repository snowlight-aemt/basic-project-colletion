package me.snowlight.springkotlinelasticsearch.model

data class QrySearch(
    val orderId: List<Long>? = null,
    val userId: List<Long>? = null,
    val keyword: String?,
    val pgStatus: List<PgStatus>?,
    val fromDt: String?,
    val toDt: String?,
    val fromAmount: Long?,
    val toAmount: Long?,
    val pageSize: Int = 10,
    val pageNext: List<Long>? = null,
)