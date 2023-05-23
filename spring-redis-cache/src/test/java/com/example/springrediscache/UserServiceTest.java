package com.example.springrediscache;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;

@DataJpaTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void getUser() {
        Users user = new Users("Test_Name");
        Long id = userService.create(user).getId();

        Users sut = userService.getUser(id);

        Assertions.assertThat(user).isEqualTo(sut);
    }

    public class UserService {
        private UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public Users create(Users user) {
            return userRepository.save(user);
        }

        public Users getUser(Long id) {
            return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        }
    }

}
