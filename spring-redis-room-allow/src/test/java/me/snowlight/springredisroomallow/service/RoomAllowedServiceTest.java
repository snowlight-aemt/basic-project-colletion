package me.snowlight.springredisroomallow.service;

import me.snowlight.springredisroomallow.model.Room;
import me.snowlight.springredisroomallow.model.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOError;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomAllowedServiceTest {
    @Autowired RoomAllowedService roomAllowedService;
    @Autowired RoomRepository roomRepository;

    @Test
    void allow() throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        int threadCount = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    Room room = this.roomRepository.findAll().stream()
                            .filter(v -> v.getRoomStatus().equals("Vacant") && v.getRoomCleanStatus().equals("Clean"))
                            .findFirst()
                            .orElseThrow(IllegalAccessError::new);

                    try {
                        this.roomAllowedService.allow(room.getId());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } finally {
                    System.out.println(  "======= ( " + countDownLatch.getCount() );
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
    }
}