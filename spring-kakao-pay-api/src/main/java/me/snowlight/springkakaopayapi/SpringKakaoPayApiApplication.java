package me.snowlight.springkakaopayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class SpringKakaoPayApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringKakaoPayApiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(30_000L))
                .setReadTimeout(Duration.ofMillis(30_000L))
                .build();
    }
}
