package me.snowlight.springbatchupdate.config;

import me.snowlight.springbatchupdate.instructure.BatchProductRepository;
import me.snowlight.springbatchupdate.instructure.SimpleProductRepository;
import me.snowlight.springbatchupdate.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Random;

@Configuration
public class AppConfig {
    @Bean
    public ProductService simpleProductService(SimpleProductRepository simpleProductRepository) {
        return new ProductService(simpleProductRepository, new Random(), Clock.systemUTC());
    }

    @Bean ProductService batchProductService(BatchProductRepository batchProductRepository) {
        return new ProductService(batchProductRepository, new Random(), Clock.systemUTC());
    }
}
