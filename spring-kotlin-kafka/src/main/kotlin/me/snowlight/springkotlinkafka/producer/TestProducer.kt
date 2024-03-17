package me.snowlight.springkotlinkafka.producer

import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {  }

@Service
@Profile("producer")
class TestProducer(
    private val template: KafkaTemplate<String, String>
) {
    fun send(topic: String, message: String) {
        logger.debug { "send to $topic : $message" }
        template.send(topic, message)
    }

}