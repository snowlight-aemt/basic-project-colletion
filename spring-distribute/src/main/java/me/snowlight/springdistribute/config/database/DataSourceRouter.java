package me.snowlight.springdistribute.config.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSourceRouter extends AbstractRoutingDataSource {
    private Map<Integer, MhaDataSource> shards ;

    private static final String MASTER = "master";
    private static final String SLAVE = "slave";

    @Override
    protected Object determineCurrentLookupKey() {
        int shardNo = getShardNo(UserHolder.getSharding());
        MhaDataSource dataSource = shards.get(shardNo);

        return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? dataSource.getSlaveName().next()
                    : dataSource.getMasterName();
    }

    private int getShardNo(UserHolder.Sharding sharding) {
        if (sharding == null) {
            return 0;
        }

        int shardNo = 0;
        ShardingProperty shardingProperty = ShardingConfig.getShardingPropertyMap().get(sharding.getTarget());
        if (shardingProperty.getStrategy() == ShardingStrategy.RANGE) {
            shardNo = getShardNoByRange(shardingProperty.getRules(), sharding.getShardKey());
        } else if (shardingProperty.getStrategy() == ShardingStrategy.MODULAR) {
            shardNo = getShardNoByModular(shardingProperty.getMod(), sharding.getShardKey());
        }

        return shardNo;
    }

    private int getShardNoByModular(int modulus, long shardKey) {
        return (int) (shardKey % modulus);
    }

    private int getShardNoByRange(List<Rule> rules, long shardKey) {
        for (var rule : rules) {
            if (rule.getRangeMin() <= shardKey && shardKey <= rule.getRangeMax()) {
                return rule.getShardNo();
            }
        }

        return 0;
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        shards = new HashMap<>();

        for (Object item: targetDataSources.keySet()) {
            String dataSourceName = item.toString();
            String shardNoStr = dataSourceName.split(FriendConfig.SHARD_DELIMITER)[0];

            MhaDataSource shard = getShard(shardNoStr);
            if (dataSourceName.contains(MASTER)) {
                shard.setMasterName(dataSourceName);
            } else if (dataSourceName.contains(SLAVE)) {
                shard.getSlaveName().add(dataSourceName);
            }
        }
    }

    private MhaDataSource getShard(String shardNoStr) {
        int shardNo = 0;

        // 체크
        shardNo = Integer.valueOf(shardNoStr);

        MhaDataSource shard = shards.get(shardNo);
        if (shard == null) {
            shard = new MhaDataSource();
            shard.setSlaveName(new RoundRobin<>(new ArrayList<>()));
            shards.put(shardNo, shard);
        }

        return shard;
    }

    @Getter
    @Setter
    private class MhaDataSource {
        private String masterName;
        private RoundRobin<String> slaveName;
    }
}
