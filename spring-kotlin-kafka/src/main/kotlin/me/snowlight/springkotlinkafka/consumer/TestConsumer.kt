package me.snowlight.springkotlinkafka.consumer

import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {  }

@Service
@Profile("consumer")
class TestConsumer {
    @KafkaListener(topics = ["test"], groupId = "A")
    fun consumerA(message: String) {
        logger.debug { "get message A: $message" }
    }

    @KafkaListener(topics = ["test"], groupId = "B")
    fun consumerB(message: String) {
        logger.debug { "get message B: $message" }
    }
}
