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


// https://hudi.blog/java-db-connection-pooling/
// https://www.baeldung.com/java-connection-pooling
// https://stackoverflow.com/questions/6137683/how-to-find-the-active-number-of-open-database-connections-in-h2-mysql
// https://passwd.tistory.com/entry/MySQL-Connection-%EC%88%98-%ED%99%95%EC%9D%B8
public class MybatisConfig {
    public static SqlSessionFactory sqlSessionFactory;

    static {
        DataSource dataSource = getDataSource();
        Environment environment = new Environment("development",
                                                    new JdbcTransactionFactory(),
                                                    dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(TeamMapper.class);

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
