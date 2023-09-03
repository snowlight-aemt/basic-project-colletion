package me.snowlight.springredisroomallow.service;

import me.snowlight.springredisroomallow.model.Room;
import me.snowlight.springredisroomallow.model.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class RoomRepositoryTest {
    @Autowired
    RoomRepository roomRepository;

    @Test
    @Transactional
    public void sss() {
        List<Room> all2 = roomRepository.findAll();

        Assertions.assertThat(all2).hasSizeGreaterThan(5);
    }
}
