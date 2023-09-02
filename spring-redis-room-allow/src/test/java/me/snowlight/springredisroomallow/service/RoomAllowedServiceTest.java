package me.snowlight.springredisroomallow.service;

import me.snowlight.springredisroomallow.TestRoomCount;
import me.snowlight.springredisroomallow.model.Room;
import me.snowlight.springredisroomallow.model.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class RoomAllowedServiceTest {
    @Autowired RoomAllowedService roomAllowedService;
    @Autowired RoomRepository roomRepository;

    @Test
    @Transactional
    void allow() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(800);
        int threadCount = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    Room room = this.roomRepository.findAll().stream()
                            .filter(v -> v.getRoomStatus().equals("Vacant") && v.getRoomCleanStatus().equals("Clean"))
                            .findFirst()
                            .orElseThrow(IllegalAccessError::new);
                    try {
                        this.roomAllowedService. allow(room.getId());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        org.assertj.core.api.Assertions.assertThat(TestRoomCount.getCount()).isEqualTo(50);
    }
}