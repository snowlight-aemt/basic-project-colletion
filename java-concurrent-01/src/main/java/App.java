import config.MybatisConfig;
import domain.TeamMapper;
import domain.Teams;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.UUID;

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
