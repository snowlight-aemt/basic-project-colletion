package me.snowlight.springredisroomallow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snowlight.springredisroomallow.TestRoomCount;
import me.snowlight.springredisroomallow.model.Room;
import me.snowlight.springredisroomallow.model.RoomHistory;
import me.snowlight.springredisroomallow.model.RoomHistoryRepository;
import me.snowlight.springredisroomallow.model.RoomRepository;
import me.snowlight.springredisroomallow.repository.RoomOccupancyRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomAllowedService {
    private final RoomOccupancyRepository roomOccupancyRepository;
//    private final EntityManager entityManager;
    private final RoomRepository roomRepository;
    private final RoomHistoryRepository roomHistoryRepository;
    private final TransactionTemplate transactionTemplate;

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void allow(Long roomId) throws InterruptedException {
        int limitTryCount = 0;
        log.info(" 엔트리 " + roomId);
        while (!roomOccupancyRepository.lock(roomId)) {
//            limitTryCount++;
//            if (limitTryCount > 3) {
//                log.info(" [-] Fail | Room Allow ===> " + roomId.toString());
//                throw new RoomNotBeAllowedException();
////                return;
//            }
//
            Thread.sleep(1);
//            throw new RoomNotBeAllowedException();
        }
//        entityManager.clear();
//        roomRepository.flush();
        Room room = this.roomRepository.findById(roomId).orElseThrow(IllegalAccessError::new);
        log.info(room.toString() + " " + room.getId() + " " + room.getRoomStatus());
        if (room.isNotAllowed()) {
            log.info(" 1 해제 " + roomId);
            roomOccupancyRepository.unlock(roomId);
            return;
        }

        room.allow();
//        log.info(room.toString());
//        log.info(room.getId() + "");
//        log.info(room.getRoomStatus());
        TestRoomCount.increase();
        log.info(" [+] Success | Room Allow ===> " + roomId.toString());
        this.roomRepository.save(room);
        this.roomHistoryRepository.save(new RoomHistory((long) TestRoomCount.getCount(), room.getId()));
//        this.roomRepository.saveAndFlush(room);
//        this.roomRepository.flush();
        log.info(" 2 해제 " + roomId);

        roomOccupancyRepository.unlock(roomId);
    }
}
