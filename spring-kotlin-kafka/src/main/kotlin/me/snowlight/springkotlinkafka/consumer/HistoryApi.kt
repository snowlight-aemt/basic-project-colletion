package me.snowlight.springkotlinkafka.consumer

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import java.time.LocalDateTime

@Component
class HistoryApi(
    @Value("\${api.history.domain}")
    private val domain: String,
) {
    private val client = WebClient.builder().baseUrl(domain)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    suspend fun save(order: Order) {
        client.post().uri("/history").bodyValue(order.toHistoryReq())
            .awaitExchange {  }
    }
}

data class ReqSaveHistory (
    var orderId: Long,
    var userId: Long?,
    var description: String?,
    var amount: Long?,
    var pgStatus: PgStatus?,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
)

data class Order(
    val id: Long = 0,
    var userId: Long,
    var description: String? = null,
    var amount: Long = 0,
    var pgOrderId: String? = null,
    var pgKey: String? = null,
    var pgStatus: PgStatus = PgStatus.CREATE,
    var pgRetryCount: Int = 0,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
) {
    fun toHistoryReq(): ReqSaveHistory {
        return this.let {ReqSaveHistory(
            this.amount,
            this.userId,
            this.description,
            this.amount,
            this.pgStatus,
            this.createdAt,
            this.updatedAt,
        )}
    }
}

enum class PgStatus {
    CREATE,
    AUTH_SUCCESS,
    AUTH_FAIL,
    AUTH_INVALID,
    CAPTURE_REQUEST,
    CAPTURE_RETRY,
    CAPTURE_SUCCESS,
    CAPTURE_FAIL,
}
