package me.snowlight.springdistribute.config.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigTest {
    @Autowired
    private ShardingDataSourceProperty friendConfig;

    @Test
    public void config() throws Exception {
        friendConfig.getShards().forEach((v) -> {
            System.out.println(v);
        });
    }
}
