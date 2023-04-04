package com.example.codingtestspringkafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodingTestSpringKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodingTestSpringKafkaApplication.class, args);
    }

//    public static void main(String[] args) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        A a = objectMapper.readValue("{\"test\":\"aaa\",\"code\":\"bbb\"}", A.class);
//        System.out.println(a);
//    }

//    @ToString
////    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    public static class A {
//        String test;
////        String code;
//
//        void setTest(String test) {
//            this.test = test;
//        }
//
////        void setCode(String code) {
////            this.code = code;
////        }
//
//        String getTest() {
//            return this.test;
//        }
//
////        String getCode() {
////            return this.code;
////        }
//    }

}
