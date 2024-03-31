package me.snowlight.springdistribute.config.database;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShardingDataSourceProperty {
    private List<Shard> shards;
}

@Getter
@Setter
class Shard {
    private String username;
    private String password;
    private Property master;
    private List<Property> slaves;
}

@Getter
@Setter
class Property {
    private String name;
    private String url;
}