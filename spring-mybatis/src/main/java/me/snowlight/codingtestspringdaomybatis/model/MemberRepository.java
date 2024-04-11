package me.snowlight.codingtestspringdaomybatis.model;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberRepository {
    public List<Map<Integer, String>> getTest();

    public List<Member> getByNameAndAge(SearchKeyword keyword);
}
