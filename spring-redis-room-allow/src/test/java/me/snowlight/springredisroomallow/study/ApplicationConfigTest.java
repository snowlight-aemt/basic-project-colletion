package me.snowlight.springredisroomallow.study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationConfigTest {
    @Value("${val.env}")
    private String env;

    @Test
    void property_value_annotation() {
        System.out.println(env);
    }
}
