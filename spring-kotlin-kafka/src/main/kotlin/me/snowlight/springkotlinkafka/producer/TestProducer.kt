package me.snowlight.springkotlinkafka.producer

import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.RoutingKafkaTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service
import reactor.kafka.sender.SenderOptions

private val logger = KotlinLogging.logger {  }

@Service
@Profile("producer")
class TestProducer(
//    private val template: KafkaTemplate<String, String>
    private val template: ReactiveKafkaProducerTemplate<String, String>,
) {
    suspend fun send(topic: String, message: String) {
        logger.debug { "send to $topic : $message" }
        template.send(topic, message).awaitSingle()
    }

}

@Configuration
class ReactiveKafkaInitializer {
    @Bean
    fun reactiveProducer(properties: KafkaProperties): ReactiveKafkaProducerTemplate<String, String> {
        return properties.buildProducerProperties()
            .let { prop -> SenderOptions.create<String, String>(prop)}
            .let { option -> ReactiveKafkaProducerTemplate(option) }
    }
}