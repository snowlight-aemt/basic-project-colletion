package me.snowlight.codingtestspringdaomybatis.model;

import lombok.Getter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;


@Mapper
public interface TestMapper {
    public List<Map<Integer, String>> getTest();
}
