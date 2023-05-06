import config.MybatisConfig;
import config.TeamMapper;
import config.Teams;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.engine.Session;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
        SqlSessionFactory config = MybatisConfig.sqlSessionFactory;
        Teams teams = new Teams();
        try (SqlSession sqlSession = config.openSession()) {
            TeamMapper teamMapper = sqlSession.getMapper(TeamMapper.class);

            teams.setName(UUID.randomUUID().toString());
            Integer save = teamMapper.save(teams);
            sqlSession.commit();
        }


        System.out.println(teams.getId());
    }
}
