package me.snowlight.springredisroomallow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snowlight.springredisroomallow.model.Room;
import me.snowlight.springredisroomallow.model.RoomRepository;
import me.snowlight.springredisroomallow.repository.RoomOccupancyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomAllowedService {
    private final RoomOccupancyRepository roomOccupancyRepository;
    private final RoomRepository roomRepository;

    public void allow(Long roomId) throws InterruptedException {
        int limitTryCount = 0;

        while (!roomOccupancyRepository.lock(roomId)) {
            limitTryCount++;
            if (limitTryCount > 3) {
                log.info(" [-] Fail | Room Allow ===> " + roomId.toString());
                throw new RoomNotBeAllowedException();
            }

            Thread.sleep(100);
        }

        Room room = this.roomRepository.findById(roomId).orElseThrow(IllegalAccessError::new);
        if (room.isNotAllowed()) {
            roomOccupancyRepository.unlock(roomId);
            return;
        }

        room.allow();
        log.info(" [+] Success | Room Allow ===> " +roomId.toString());
        roomOccupancyRepository.unlock(roomId);
    }
}
