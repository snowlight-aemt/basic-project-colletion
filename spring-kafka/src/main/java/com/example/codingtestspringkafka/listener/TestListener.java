package com.example.codingtestspringkafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;

@Slf4j
@Component
public class TestListener {
    @RetryableTopic(attempts = "6", backoff = @Backoff(delay = 1000L, multiplier = 10.0))
    @KafkaListener(topics = {"test-topic-01"}, groupId = "test-topic-01-group", containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Headers MessageHeaders messageHeaders, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String message, Acknowledgment ack) {
        log.info(message);
        log.info(topic);
//        if (!message.equals("OK")) {
//            // return 바로 거절 lag O
//            // throw  재 시도  lag X
//
//            log.info("3333333333333333");
//            throw new IllegalArgumentException();
////            return;
//        }

        System.out.println(message);
        ack.acknowledge();
    }



//    @Bean
//    public ConsumerFactory<String, String> consumerFactory(KafkaProperties kafkaProperties) {
//        final var props = Map.of()
//    }
////
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(KafkaProperties kafkaProperties, KafkaTemplate<String, String> kafkaTemplate) {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        CommonErrorHandler commonErrorHandler = new DefaultErrorHandler(((consumerRecord, e) -> {
//            log.info("에러?");
//        }), new FixedBackOff(1000, 5));
//
//        factory.setCommonErrorHandler(commonErrorHandler);
//        return factory;
//    }
}

