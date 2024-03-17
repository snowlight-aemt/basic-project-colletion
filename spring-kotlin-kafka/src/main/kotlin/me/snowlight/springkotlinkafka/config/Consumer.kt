package me.snowlight.springkotlinkafka.config

import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Service
import reactor.kafka.receiver.ReceiverOptions

private val logger = KotlinLogging.logger {  }

@Service
class Consumer(
    private val properties: KafkaProperties,
) {
    fun consumer(topic: String, groupId: String, runner: (recode: ConsumerRecord<String, String>) -> Unit) {
        properties.buildConsumerProperties().let { prop ->
                prop[ConsumerConfig.GROUP_ID_CONFIG] = groupId
                ReceiverOptions.create<String, String>(prop)
            }.let { option ->
                option.subscription(listOf(topic))
            }.let { option ->
                ReactiveKafkaConsumerTemplate(option)
            }.let { consumer ->
                consumer.receiveAutoAck().flatMap { recode ->
                    mono {
//                        logger.debug { "$recode" }
                        runner.invoke(recode)
                    }
                }.subscribe()
            }
    }
}