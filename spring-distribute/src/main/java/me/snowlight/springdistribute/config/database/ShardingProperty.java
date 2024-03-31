package me.snowlight.springdistribute.config.database;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ShardingProperty {
    private ShardingStrategy strategy;
    private List<Rule> rules;
    private int mod;
}

@Getter
@Setter
class Rule {
    private int shardNo;
    private long rangeMin;
    private long rangeMax;
}

