package me.snowlight.codingtestspringdaomybatis;

import me.snowlight.codingtestspringdaomybatis.model.TestMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

//@ContextConfiguration(classes = {AppConfig.class, MemberService.class})
//@ContextConfiguration(classes = CodingTestSpringDaoMybatisApplication.class)
//@ContextConfiguration(classes = {AppConfig.class, MemberService.class})
//@JdbcTest()
//@SpringBootTest
//@JdbcTest
//@ContextConfiguration(classes = {AppConfig.class, TestMapper.class})
//@SpringBootTest
//@ActiveProfiles("test")
//@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
//@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@ContextConfiguration(classes = {CodingTestSpringDaoMybatisApplication.class, AppConfig.class, TestMapper.class})
@ActiveProfiles("test")
//@TestPropertySource(locations ={"classpath:application-test.yml"})
class CodingTestSpringDaoMybatisApplicationTests {

//    @Autowired
//    MemberService memberService;

    @Autowired
    TestMapper testMapper;

    @Test
    void contextLoads() {
//        System.out.println(testMapper);
//        System.out.println(memberService);
        System.out.println(testMapper);

//        Assertions.assertNotNull(testMapper);
//        List<Map<Integer, String>> test = memberService.getTest();
//        Assertions.assertNotNull(test.get(0).get("NAME"));
    }

}
