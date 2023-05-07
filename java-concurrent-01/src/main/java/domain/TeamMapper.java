package domain;

import domain.Teams;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeamMapper {
    @Select("select * from teams where id = #{id}")
    public Teams findById(Integer id);

    @Insert("insert into Teams (name) values (#{teams.name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public Integer save(@Param("teams") Teams teams);
}
