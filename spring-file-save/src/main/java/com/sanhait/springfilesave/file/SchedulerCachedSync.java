package com.sanhait.springfilesave.file;

import com.sanhait.springfilesave.file.dto.Reservation;
import com.sanhait.springfilesave.file.dto.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerCachedSync {

    public static final int TEST_BASH_CNT = 10;
    
    // TODO 다음 날로 거기 전에 캐시 데이터 전부 이관
    @Scheduled(cron = "0 58 23 * * *")
    public void sync() {
        System.out.println("마감");
        CacheRoomHistoryRepository.backupCacheRoom();
        CacheRoomHistoryRepository.backupCacheReservation();
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void syncCash() {
        System.out.println("캐시 저장");
        CacheRoomHistoryRepository.cacheRoom();
        CacheRoomHistoryRepository.cacheReservation();
    }

    @Scheduled(cron = "0 0 0-22 * * *")
    public void syncFile() {
        System.out.println("파일 저장");
        // TODO 처음 실행됨.. ? 로드한 데이터를 삭제할 수 있다.
        CacheRoomHistoryRepository.backupCacheRoom();
        CacheRoomHistoryRepository.backupCacheReservation();
    }

    public static int counttt = 0;

//    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void asdfasdf() {

//        log.info("테스트 데이터 입력");
        List<Room> rooms = Arrays.asList(
                new Room("0304", Room.RoomCleanStatus.C, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now()),
                new Room("0305", Room.RoomCleanStatus.G, Room.RoomStatus.T, Room.Status.SUCCESS, LocalDateTime.now()),
                new Room("0306", Room.RoomCleanStatus.K, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now()),
                new Room("0307", Room.RoomCleanStatus.C, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now())
        );

        List<Reservation> reservations = Arrays.asList(
                new Reservation("0305", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now()),
                new Reservation("0307", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now()),
                new Reservation("0305", Reservation.ReservationStatus.CHECKOUT, Reservation.Status.SUCCESS, LocalDateTime.now()),
                new Reservation("0306", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now())
        );

        ExecutorService executorService = Executors.newFixedThreadPool(200);

        int count = (TEST_BASH_CNT * 2);
        CountDownLatch countDownLatch = new CountDownLatch(count);

        Random random = new Random(System.currentTimeMillis());
//        int iii = 200 / 10;
        int cnt = 0;
        while (true) {

            executorService.submit(() -> {
                for (int j = 0; j < TEST_BASH_CNT; j++) {
                    int i = random.nextInt(4);
                    CacheRoomHistoryRepository.saveRoomCached(rooms.get(i).clone());
//                    fileQueue.enQueueUpdateRoom(rooms.get(i).clone());
                    countDownLatch.countDown();
                }
            });
            cnt += TEST_BASH_CNT;

            executorService.submit(() -> {
                for (int j = 0; j < TEST_BASH_CNT; j++) {
                    int i = random.nextInt(4);
                    CacheRoomHistoryRepository.saveReservationCached(reservations.get(i).clone());
//                    fileQueue.enQueueCheckInAndOut(reservations.get(i).clone());
                    countDownLatch.countDown();
                }
            });
            cnt += TEST_BASH_CNT;

            if (cnt >= count) {
                break;
            }
        }

        counttt += countDownLatch.getCount();


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
