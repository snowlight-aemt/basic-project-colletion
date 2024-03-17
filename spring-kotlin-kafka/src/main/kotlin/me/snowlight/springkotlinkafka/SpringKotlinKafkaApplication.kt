package me.snowlight.springkotlinkafka

import me.snowlight.springkotlinkafka.producer.TestProducer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class SpringKotlinKafkaApplication(
    @Value("\${spring.profiles.active:consumer}")
    private val profile: String,
    private val applicationContext: ApplicationContext,
):ApplicationRunner  {
    override fun run(args: ApplicationArguments?) {
        if (profile == "producer") {
            val testProducer = applicationContext.getBean("testProducer", TestProducer::class) as TestProducer

            testProducer.send("test", "test message")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringKotlinKafkaApplication>(*args)
}
