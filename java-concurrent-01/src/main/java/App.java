import config.MybatisConfig;
import config.TeamMapper;
import config.Teams;
import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.engine.Session;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
        SqlSessionFactory config = MybatisConfig.sqlSessionFactory;
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        Runnable runnable = () -> {
            {
                try (var session = config.openSession()) {
                    System.out.println(Thread.currentThread().getName());
//                      Thread.sleep(30000); // Session 없음.

                    TeamMapper mapper = session.getMapper(TeamMapper.class);
                    mapper.findById(1);
//                        Teams teams = session.<Teams>selectOne("config.TeamMapper.findById", 1);
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
