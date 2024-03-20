package me.snowlight.springkotlinelasticsearch

import kotlinx.coroutines.runBlocking
import me.snowlight.springkotlinelasticsearch.config.extension.toLocalDate
import me.snowlight.springkotlinelasticsearch.model.History
import me.snowlight.springkotlinelasticsearch.model.HistoryNativeRepository
import me.snowlight.springkotlinelasticsearch.model.HistoryRepository
import me.snowlight.springkotlinelasticsearch.model.PgStatus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
class SpringKotlinElasticsearchApplicationTests(
    @Autowired private val repository: HistoryRepository,
) {

    @Test
    fun contextLoads() {
        runBlocking {
            listOf(
                History(orderId = 1, userId = 11, description = "apple", amount = 1000, pgStatus =  PgStatus.CAPTURE_REQUEST, createdAt = "2024-01-01".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 2, userId = 11, description = "mango", amount = 1100, pgStatus =  PgStatus.CAPTURE_RETRY, createdAt = "2024-01-02".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 3, userId = 11, description = "orange,mango", amount = 1300, pgStatus =  PgStatus.CAPTURE_SUCCESS, createdAt = "2024-01-03".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 4, userId = 11, description = "pineapple,mango", amount = 1400, pgStatus =  PgStatus.CAPTURE_REQUEST, createdAt = "2024-01-04".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 5, userId = 11, description = "boiled egg", amount = 1500, pgStatus =  PgStatus.CAPTURE_RETRY, createdAt = "2024-01-05".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 6, userId = 11, description = "fried egg", amount = 1600, pgStatus =  PgStatus.CAPTURE_SUCCESS, createdAt = "2024-01-06".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 7, userId = 11, description = "apple tomato", amount = 1700, pgStatus =  PgStatus.CAPTURE_REQUEST, createdAt = "2024-01-07".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 8, userId = 11, description = "boiled egg", amount = 1800, pgStatus =  PgStatus.CAPTURE_RETRY, createdAt = "2024-01-08".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 9, userId = 12, description = "apply egg", amount = 1990, pgStatus =  PgStatus.CAPTURE_SUCCESS, createdAt = "2024-01-09".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 10, userId = 12, description = "mango,apple", amount = 2000, pgStatus =  PgStatus.CAPTURE_REQUEST, createdAt = "2024-01-10".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 11, userId = 12, description = "mango,crown", amount = 2100, pgStatus =  PgStatus.CAPTURE_RETRY, createdAt = "2024-01-11".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 12, userId = 12, description = "apply,mango", amount = 2200, pgStatus =  PgStatus.CAPTURE_SUCCESS, createdAt = "2024-01-12".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),
                History(orderId = 13, userId = 12, description = "boiled orange", amount = 2300, pgStatus =  PgStatus.CAPTURE_REQUEST, createdAt = "2024-01-13".toLocalDateTime(), updatedAt = "2024-01-01".toLocalDateTime()),

            ).forEach {
                repository.save(it)
            }


        }
    }

}




fun String.toLocalDateTime(format: String = "yyyyMMdd") = this.toLocalDate().atStartOfDay()