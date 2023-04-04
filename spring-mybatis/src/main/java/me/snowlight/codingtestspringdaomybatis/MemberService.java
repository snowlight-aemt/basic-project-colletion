package me.snowlight.codingtestspringdaomybatis;

import me.snowlight.codingtestspringdaomybatis.model.Member;
import me.snowlight.codingtestspringdaomybatis.model.TestMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberService {

    @Autowired
    TestMapper testMapper;

    @Value("${spring.application.name}")
    public String name;
    @Value("${spring.application.name2}")
    public String name2;

    public List<Map<Integer, String>> getTest() {
        List<Map<Integer, String>> test = testMapper.getTest();
        System.out.println(name);
        System.out.println(name2);
        System.out.println(test);
        return test;
    }
}
