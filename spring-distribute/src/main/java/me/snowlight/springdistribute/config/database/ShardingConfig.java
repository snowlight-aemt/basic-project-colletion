package me.snowlight.springdistribute.config.database;

import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Setter
public class ShardingConfig {
    private static Map<ShardingTarget, ShardingProperty> shardingPropertyMap = new ConcurrentHashMap<>();

    public static Map<ShardingTarget, ShardingProperty> getShardingPropertyMap() {
        return shardingPropertyMap;
    }
}
