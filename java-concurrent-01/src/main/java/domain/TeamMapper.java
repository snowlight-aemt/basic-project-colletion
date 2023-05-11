package domain;

import domain.Teams;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamMapper {
    @Select("select * from teams where id = #{id}")
    public Teams findById(Integer id);

    @Select("select * from teams")
    public List<Teams> findAll();

    @Delete("delete from teams")
    public Integer deleteAll();

    @Insert("insert into Teams (name, team_token) values (#{teams.name}, #{teams.teamToken})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public Integer save(@Param("teams") Teams teams);
}
