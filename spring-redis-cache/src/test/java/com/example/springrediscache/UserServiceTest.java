package com.example.springrediscache;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@EnableCaching
@ActiveProfiles("test")
=======
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@DataJpaTest
@EnableCaching
>>>>>>> dcb8a66 (DB-batch Project Init)
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

<<<<<<< HEAD
        Users first = userService.getUser(id);
        Users second = userService.getUser(id);
        Users third = userService.getUser(id);

        Assertions.assertThat(first).isEqualTo(first);
        Assertions.assertThat(second).isEqualTo(second);
        Assertions.assertThat(user).isEqualTo(third);
=======
        Users sut = userService.getUser(id);
        Users sut1 = userService.getUser(id);
        Users sut2 = userService.getUser(id);

        Assertions.assertThat(user).isEqualTo(sut2);
    }

    public class UserService {
        private UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public Users create(Users user) {
            return userRepository.save(user);
        }

        @Cacheable(key = "#id")
        public Users getUser(Long id) {
            return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        }
>>>>>>> dcb8a66 (DB-batch Project Init)
    }

}
