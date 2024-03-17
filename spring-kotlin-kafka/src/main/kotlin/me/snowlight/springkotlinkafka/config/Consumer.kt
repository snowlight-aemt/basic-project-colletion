package me.snowlight.springkotlinkafka.config

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Service
import reactor.kafka.receiver.ReceiverOptions
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

private val logger = KotlinLogging.logger {  }

@Service
@Profile("consumer")
class Consumer(
    private val properties: KafkaProperties,
    private val redisTemplate: ReactiveRedisTemplate<Any, Any>,
) {
    private val ops = redisTemplate.opsForValue()

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
                    val key = getKey(recode, groupId)
                    mono {
                        if (ops.setIfAbsent(key, true).awaitSingle()) {
//                        logger.debug { "$recode" }
                            redisTemplate.expire(key, 5.minutes.toJavaDuration()).awaitSingle()
                            runner.invoke(recode)
                        }
                    }
                }.subscribe()
            }
    }

    fun getKey(recode: ConsumerRecord<String, String>, groupId: String): String {
        return "kafka-consumer/${recode.topic()}/${recode.partition()}/${groupId}/${recode.offset()}"
    }
}