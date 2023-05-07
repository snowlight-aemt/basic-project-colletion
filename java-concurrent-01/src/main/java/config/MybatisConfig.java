package config;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class MybatisConfig {
    public static SqlSessionFactory sqlSessionFactory;

    static {
        DataSource dataSource = getDataSource();
        Environment environment = new Environment("development",
                                                    new JdbcTransactionFactory(),
                                                    dataSource);
        Configuration configuration = new Configuration(environment);

        // Mapper 방법
        // 1 @Mapper
        configuration.addMappers("domain");
        // 2 단 건 적용
        // configuration.addMapper(TeamMapper.class);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    private static DataSource getDataSource() {
        DataSourceFactory dataSourceFactory = new PooledDataSourceFactory();

        Properties properties = new Properties();
        properties.setProperty("driver", "org.h2.Driver");
//        properties.setProperty("driver", "com.p6spy.engine.spy.P6SpyDriver");
        properties.setProperty("url", "jdbc:h2:tcp://localhost/~/study/h2_db/teams-db");
//        properties.setProperty("url", "jdbc:p6spy:h2:tcp://localhost/~/study/h2_db/teams-db");
        properties.setProperty("username", "sa");
        properties.setProperty("password", "");
        properties.setProperty("poolMaximumActiveConnections", "30");

        dataSourceFactory.setProperties(properties);
        return dataSourceFactory.getDataSource();
    }
}
