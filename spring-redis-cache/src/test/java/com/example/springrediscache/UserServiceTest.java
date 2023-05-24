package com.example.springrediscache;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@EnableCaching
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
//        userService = new UserService(userRepository);
    }

//    @Transactional
    @Test
    void getUser() {
        Users user = new Users("Test_Name");
        Long id = userService.create(user).getId();

        Users first = userService.getUser(id);
        Users second = userService.getUser(id);
        Users third = userService.getUser(id);

        Assertions.assertThat(first).isEqualTo(first);
        Assertions.assertThat(second).isEqualTo(second);
        Assertions.assertThat(user).isEqualTo(third);
    }

}
