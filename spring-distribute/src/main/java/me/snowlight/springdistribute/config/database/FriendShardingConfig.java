package me.snowlight.springdistribute.config.database;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "sharding")
public class FriendShardingConfig {
    private ShardingProperty friend;

    @PostConstruct
    public void init() {
        log.info("SYSTEM - POST CONSTRUCT");
//        ShardingConfig.getShardingPropertyMap().put(ShardingTarget.FRIEND, friend);
    }
}