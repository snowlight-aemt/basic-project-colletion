package me.snowlight.springkotlinkafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import me.snowlight.springkotlinkafka.config.Consumer
import me.snowlight.springkotlinkafka.config.TOPIC_PAYMENT
import mu.KotlinLogging
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import kotlin.math.log

private val logger = KotlinLogging.logger {  }

@Configuration
class OrderConsumer(
    private val consumer: Consumer,
    private val historyApi: HistoryApi,
    private val objectMapper: ObjectMapper,
): InitializingBean {

    override fun afterPropertiesSet() {
        consumer.consumer(TOPIC_PAYMENT, "es") { record ->
            objectMapper.readValue(record.value(), Order::class.java).let { order ->
                logger.debug { ">> es: $order" }
                historyApi.save(order)
            }
        }

        var total = 0L
        consumer.consumer(TOPIC_PAYMENT, "sum") { record ->
            objectMapper.readValue(record.value(), Order::class.java).let { order ->
                logger.debug { ">> sum: $order" }
                if (order.pgStatus == PgStatus.CAPTURE_SUCCESS) {
                    total += order.amount
                    logger.debug { " - total: $total" }
                }
            }
        }
    }
}