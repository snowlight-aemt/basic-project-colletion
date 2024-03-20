package me.snowlight.springkotlinelasticsearch.controller

import me.snowlight.springkotlinelasticsearch.model.History
import me.snowlight.springkotlinelasticsearch.model.PgStatus
import java.time.LocalDateTime

data class ReqSaveHistory (
    var orderId: Long,
    var userId: Long?,
    var description: String?,
    var amount: Long?,
    var pgStatus: PgStatus?,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
) {
    fun toHistory(): History {
        return this.let {
            History(
                orderId = orderId,
                userId = userId ?: 0,
                description = description ?: "",
                amount = amount ?: 0,
                pgStatus = pgStatus ?: PgStatus.CREATE,
                createdAt = createdAt?: LocalDateTime.now(),
                updatedAt = updatedAt?: LocalDateTime.now(),
            )
        }
    }
}