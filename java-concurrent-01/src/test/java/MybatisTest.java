import config.MybatisConfig;
import domain.TeamMapper;
import domain.Teams;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MybatisTest {

    @Test
    void insertTest() {
        SqlSessionFactory config = MybatisConfig.sqlSessionFactory;

        try (SqlSession sqlSession = config.openSession()) {
            TeamMapper teamMapper = sqlSession.getMapper(TeamMapper.class);
            Teams teams = new Teams();
            teams.setName(UUID.randomUUID().toString());
            teamMapper.save(teams);
            sqlSession.commit();
        }
    }

    @Test
    void selectTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        SqlSessionFactory config = MybatisConfig.sqlSessionFactory;

        Runnable runnable = () -> {
            {
                try (var session = config.openSession()) {
                    System.out.println(Thread.currentThread().getName());
//                      Thread.sleep(30000); // Session 없음.

                    TeamMapper mapper = session.getMapper(TeamMapper.class);
                    mapper.findById(1);
//                        Teams teams = session.<Teams>selectOne("domain.TeamMapper.findById", 1);
                    Thread.sleep(1000); // Session 존재.

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        for (int i = 0 ; i < 50 ; i++) {
            executorService.submit(runnable);
        }

        executorService.shutdown();
    }

}
