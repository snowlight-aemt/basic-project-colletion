package com.example.codingtestspringkafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class Config {



//    @Override
//    public void configure(ConcurrentKafkaListenerContainerFactory<Object, Object> listenerFactory, ConsumerFactory<Object, Object> consumerFactory) {
//        super.configure(listenerFactory, consumerFactory);
//
//        CommonErrorHandler commonErrorHandler = new DefaultErrorHandler(((consumerRecord, e) -> {
//            log.info("에러?");
//        }), new FixedBackOff(1000, 5));
//
//        listenerFactory.setCommonErrorHandler(commonErrorHandler);
//    }
}

