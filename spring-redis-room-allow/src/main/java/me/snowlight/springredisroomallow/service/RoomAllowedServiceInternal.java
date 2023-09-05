package me.snowlight.springredisroomallow.service;

import lombok.extern.slf4j.Slf4j;
import me.snowlight.springredisroomallow.CountClass;
import me.snowlight.springredisroomallow.model.Room;
import me.snowlight.springredisroomallow.model.RoomHistory;
import me.snowlight.springredisroomallow.model.RoomHistoryRepository;
import me.snowlight.springredisroomallow.model.RoomRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class RoomAllowedServiceInternal {
    private final RoomRepository roomRepository;
    private final RoomHistoryRepository roomHistoryRepository;

    public RoomAllowedServiceInternal(RoomRepository roomRepository, RoomHistoryRepository roomHistoryRepository) {
        this.roomRepository = roomRepository;
        this.roomHistoryRepository = roomHistoryRepository;
    }

    @Transactional // <-- 다중 스레드를 사용하는 경우 이슈가 발생함.
    public void allow(Long roomId) {
        Room room = this.roomRepository.findById(roomId).orElseThrow(IllegalAccessError::new);
        if (room.isNotAllowed()) {
            return;
        }

        room.allow();
        CountClass.increase(); // 실제 실행 카운트
        log.info(" [+] Success | Room Allow ===> " + roomId.toString());
        this.roomRepository.save(room);
        this.roomHistoryRepository.save(new RoomHistory((long) CountClass.getCount(), room.getId()));
    }
}
