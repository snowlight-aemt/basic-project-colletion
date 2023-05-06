package config;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface TeamMapper {
    @Select("select * from teams where id = #{id}")
    public Teams findById(Integer id);

    @Insert("insert into Teams (name) values (#{teams.name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public Integer save(@Param("teams") Teams teams);
}
