package me.snowlight.springkotlinkafka

import kotlinx.coroutines.runBlocking
import me.snowlight.springkotlinkafka.config.Consumer
import me.snowlight.springkotlinkafka.producer.TestProducer
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.kafka.annotation.EnableKafka

private val logger = KotlinLogging.logger {  }

@SpringBootApplication
@EnableKafka
class SpringKotlinKafkaApplication(
    @Value("\${spring.profiles.active:consumer}")
    private val profile: String,
    private val applicationContext: ApplicationContext,
):ApplicationRunner  {
    override fun run(args: ApplicationArguments?) {
        if (profile == "consumer") {
            val consumer = applicationContext.getBean("consumer", Consumer::class) as Consumer
            logger.debug { "ready consumer" }
            consumer.consumer("test", "A") {
                logger.debug { "[reactive kafka] get message : $it" }
            }
            consumer.consumer("test", "B") {
                logger.debug { "[reactive kafka] get message : $it" }
            }
        }

        if (profile == "producer") {
            val testProducer = applicationContext.getBean("testProducer", TestProducer::class) as TestProducer
            runBlocking {
                testProducer.send("test", "test message")
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringKotlinKafkaApplication>(*args)
}
