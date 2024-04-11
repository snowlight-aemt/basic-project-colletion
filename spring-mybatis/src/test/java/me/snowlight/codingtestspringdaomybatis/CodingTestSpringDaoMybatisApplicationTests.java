package me.snowlight.codingtestspringdaomybatis;

import me.snowlight.codingtestspringdaomybatis.config.AppConfig;
import me.snowlight.codingtestspringdaomybatis.model.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

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
@ContextConfiguration(classes = {CodingTestSpringDaoMybatisApplication.class, AppConfig.class, MemberRepository.class})
@ActiveProfiles("test")
//@TestPropertySource(locations ={"classpath:application-test.yml"})
class CodingTestSpringDaoMybatisApplicationTests {

//    @Autowired
//    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void contextLoads() {
//        System.out.println(testMapper);
//        System.out.println(memberService);
        System.out.println(memberRepository);

//        Assertions.assertNotNull(testMapper);
//        List<Map<Integer, String>> test = memberService.getTest();
//        Assertions.assertNotNull(test.get(0).get("NAME"));
    }

}
