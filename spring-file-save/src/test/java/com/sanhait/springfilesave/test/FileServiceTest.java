package com.sanhait.springfilesave.test;

import com.sanhait.springfilesave.file.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@SpringBootTest
class FileServiceTest {
    private static final Logger log = LoggerFactory.getLogger(FileServiceTest.class);

    @Test
    void save() {
//        FileQueue fileQueue = new FileQueue();

//        fileQueue.init();

//        RoomHistoryRepository.deleteAll();

        List<Room> rooms = Lists.list(
                new Room("0304", Room.RoomCleanStatus.C, Room.RoomStatus.E, "SUCCESS", LocalDateTime.now()),
                new Room("0305", Room.RoomCleanStatus.G, Room.RoomStatus.T, "SUCCESS", LocalDateTime.now()),
                new Room("0306", Room.RoomCleanStatus.K, Room.RoomStatus.E, "SUCCESS", LocalDateTime.now()),
                new Room("0307", Room.RoomCleanStatus.C, Room.RoomStatus.E, "SUCCESS", LocalDateTime.now())
        );

        List<Reservation> reservations = Lists.list(
            new Reservation("0305", Reservation.ReservationStatus.CHECKIN, "SUCCESS", LocalDateTime.now()),
            new Reservation("0307", Reservation.ReservationStatus.CHECKIN, "SUCCESS", LocalDateTime.now()),
            new Reservation("0305", Reservation.ReservationStatus.CHECKOUT, "SUCCESS", LocalDateTime.now()),
            new Reservation("0306", Reservation.ReservationStatus.CHECKIN, "SUCCESS", LocalDateTime.now())
        );

        ExecutorService executorService = Executors.newFixedThreadPool(200);

        int count = 200 * 1;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        Random random = new Random(System.currentTimeMillis());
//        int iii = 200 / 10;
        int cnt = 0;
        while (true) {

            executorService.submit(() -> {
                log.info(Thread.currentThread().getName());
                for (int j = 0; j < 100; j++) {
                    int i = random.nextInt(4);
                    RoomHistoryRepository.saveRoom(rooms.get(i).clone());
//                    fileQueue.enQueueUpdateRoom(rooms.get(i).clone());
                    countDownLatch.countDown();
                }
            });
            cnt += 100;

            executorService.submit(() -> {
                log.info(Thread.currentThread().getName());
                for (int j = 0; j < 100; j++) {
                    int i = random.nextInt(4);
                    RoomHistoryRepository.saveReservation(reservations.get(i).clone());
//                    fileQueue.enQueueCheckInAndOut(reservations.get(i).clone());
                    countDownLatch.countDown();
                }
            });
            cnt += 100;

            if (cnt >= count) {
                break;
            }
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("끝");
        Assertions.assertThat(RoomHistoryRepository.seqNo == count);

    }

    @Test
    void direct() {
//        try {
//            Files.list(Paths.get("C:\\log\\20240920")).forEach(s -> {
//                if (Files.isDirectory(s)) {
//                    System.out.println(s.getFileName());
//                }
//            });
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    void save1() {
//        FileQueue fileQueue = new FileQueue();
//        for (int i=0; i<10; i++) {
////            log.info("sdf");
////            Room room = new Room("0304", RoomCleanStatus.C, RoomStatus.E, "SUCCESS", LocalDateTime.now());
////            fileQueue.enQueueInHouse(new InHouseWithRoom(UUID.randomUUID().toString(), room));
//        }
    }
}



