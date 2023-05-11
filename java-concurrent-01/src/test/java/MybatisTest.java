import config.MybatisConfig;
import domain.TeamMapper;
import domain.Teams;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class MybatisTest {

    @BeforeEach
    public void start() {
        SqlSessionFactory config = MybatisConfig.sqlSessionFactory;
        try (var session = config.openSession()) {
            TeamMapper mapper = session.getMapper(TeamMapper.class);
            Integer integer = mapper.deleteAll();
            session.commit();
        }
    }

    @AfterEach
    public void end() {
        SqlSessionFactory config = MybatisConfig.sqlSessionFactory;
        try (var session = config.openSession()) {
            TeamMapper mapper = session.getMapper(TeamMapper.class);
            Integer integer = mapper.deleteAll();
            session.commit();
        }
    }

    static int cnt = 0;
    @RepeatedTest(5)
    void concurrentTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        SqlSessionFactory config = MybatisConfig.sqlSessionFactory;

        Callable<String> callable = () -> {
            try (var session = config.openSession()) {
//                System.out.println(Thread.currentThread().getName());
                TeamMapper mapper = session.getMapper(TeamMapper.class);
                Teams teams = new Teams();
                teams.setTeamToken(UUID.randomUUID().toString());
                teams.setName("TEST_");
                mapper.save(teams);
                Thread.sleep(10); // 작업
                session.commit();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "OK";
        };

        int taskCount = 15000;

        Future<String> submit = null;
        for (int i = 0; i < taskCount; i++) {
            submit = executorService.submit(callable);
        }

        submit.get();
        executorService.shutdown();

        try (var session = config.openSession()) {
            TeamMapper mapper = session.getMapper(TeamMapper.class);
            List<Teams> teams = mapper.findAll();
            Assertions.assertEquals(taskCount, teams.size());
        }
    }

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
