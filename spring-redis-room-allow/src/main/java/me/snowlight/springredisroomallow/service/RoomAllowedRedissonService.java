package me.snowlight.springredisroomallow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snowlight.springredisroomallow.CountClass;
import me.snowlight.springredisroomallow.model.Room;
import me.snowlight.springredisroomallow.model.RoomHistory;
import me.snowlight.springredisroomallow.model.RoomHistoryRepository;
import me.snowlight.springredisroomallow.model.RoomRepository;
import me.snowlight.springredisroomallow.repository.RoomLettuceRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomAllowedRedissonService {
    private final RedissonClient redissonClient;
    private final RoomRepository roomRepository;
    private final RoomHistoryRepository roomHistoryRepository;

//    @Transactional // <-- 다중 스레드를 사용하는 경우 이슈가 발생함.
    public void  allow(Long roomId) throws InterruptedException {
        RLock lock = redissonClient.getLock(roomId.toString());

        boolean available = lock.tryLock(15, 1, TimeUnit.SECONDS);

        try {
            if (!available) {
//                log.info("lock 획득 실패");
                return;
            }

            Room room = this.roomRepository.findById(roomId).orElseThrow(IllegalAccessError::new);
            if (room.isNotAllowed()) {
                lock.unlock();
                return;
            }

            room.allow();
            CountClass.increase(); // 실제 실행 카운트
            log.info(" [+] Success | Room Allow ===> " + roomId.toString());
            this.roomRepository.save(room);
            this.roomHistoryRepository.save(new RoomHistory((long) CountClass.getCount(), room.getId()));
        } finally {
            lock.unlock();
        }
    }
}
