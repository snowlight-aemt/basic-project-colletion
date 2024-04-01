package me.snowlight.springdistribute.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
//@EnableJpaRepositories
//@Order(Integer.MAX_VALUE)
@ConfigurationProperties(prefix = "datasource")
@Setter
@DependsOn(value = {"friendShardingConfig"})
public class FriendConfig {
    private ShardingDataSourceProperty friend;
    public static final String SHARD_DELIMITER = "-";

    @Bean
    public DataSource friendDataSource() {
        DataSourceRouter router = new DataSourceRouter();
        Map<Object, Object> dataSourceMap = new LinkedHashMap<>();

        for (int i = 0; i < friend.getShards().size(); i++) {
            Shard shard = friend.getShards().get(i);

            DataSource masterDs = datasource(shard.getUsername(), shard.getPassword(), shard.getMaster().getUrl());
            dataSourceMap.put(i + SHARD_DELIMITER + shard.getMaster().getName(), masterDs);

            for (var slave: shard.getSlaves()) {
                DataSource slaveDs = datasource(shard.getUsername(), shard.getPassword(), slave.getUrl());
                dataSourceMap.put(i + SHARD_DELIMITER + slave.getName(), slaveDs);
            }
        }

        router.setTargetDataSources(dataSourceMap); // dataSourceMap
        router.afterPropertiesSet();

        // LEARN Datasource 의 Connection 획득이 쿼리 호출 시에 이루어지도록 하여
        //  Router 가 determineCurrentLookupKey 메소드를 통해 타켓 데이터 소스를 결정할 수 있다.
        return new LazyConnectionDataSourceProxy(router);
    }

    private DataSource datasource(String username, String password, String jdbcUrl) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        return new HikariDataSource(hikariConfig);
    }

}
