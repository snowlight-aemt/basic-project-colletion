package com.sanhait.springfilesave;

import com.sanhait.springfilesave.file.CacheRoomHistoryRepository;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@EnableScheduling
@SpringBootApplication
public class SpringFileSaveApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringFileSaveApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringFileSaveApplication.class, args);
    }

    @PreDestroy
    public void onShutdown() {
        log.info("서비스 종료 로직");

        CacheRoomHistoryRepository.backupCacheRoom();
        CacheRoomHistoryRepository.backupCacheReservation();
    }
}
