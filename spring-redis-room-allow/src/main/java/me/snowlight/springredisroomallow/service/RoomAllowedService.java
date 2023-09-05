package me.snowlight.springredisroomallow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snowlight.springredisroomallow.model.RoomHistoryRepository;
import me.snowlight.springredisroomallow.model.RoomRepository;
import me.snowlight.springredisroomallow.repository.RoomLettuceRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomAllowedService {
    private final RoomLettuceRepository roomOccupancyRepository;
    private final RoomAllowedServiceInternal roomAllowedServiceInternal;

    public void  allow(Long roomId) throws InterruptedException {
        while (!roomOccupancyRepository.lock(roomId)) {
            throw new RoomNotBeAllowedException();
        }

        try {
            roomAllowedServiceInternal.allow(roomId);

        } finally {
            roomOccupancyRepository.unlock(roomId);
        }
    }
}
