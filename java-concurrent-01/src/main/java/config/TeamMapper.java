package config;

import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface TeamMapper {
    @Select("select * from teams where id = #{id}")
    public Teams findById(Integer id);
}
