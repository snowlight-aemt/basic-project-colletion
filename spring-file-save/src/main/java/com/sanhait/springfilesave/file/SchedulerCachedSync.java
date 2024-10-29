package com.sanhait.springfilesave.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerCachedSync {
    @Scheduled(cron = "0 57 23 * * *")
    public void sync() {
        log.info("마감");
        log.info("캐시 저장");
        log.info("캐시 파일을 로그 파일로 이동(저장)");

        CacheRoomHistoryRepository.cacheRoom();
        CacheRoomHistoryRepository.cacheReservation();
        CacheRoomHistoryRepository.backupCacheRoom();
        CacheRoomHistoryRepository.backupCacheReservation();

        deleteLog();
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void syncCash() {
        log.info("캐시 저장");
        CacheRoomHistoryRepository.cacheRoom();
        CacheRoomHistoryRepository.cacheReservation();
    }

    @Scheduled(cron = "0 0 0-22 * * *")
    public void syncFile() {
        log.info("파일 저장");
        log.info("캐시 파일을 로그 파일로 이동(저장)");

        CacheRoomHistoryRepository.backupCacheRoom();
        CacheRoomHistoryRepository.backupCacheReservation();
    }





    void deleteLog() {
        LocalDate lineDate = LocalDate.now().minusDays(30);

        try (Stream<Path> list = Files.list(Util.getRoot())) {
            List<File> directories = list.map(Path::toFile)
                    .filter(File::isDirectory)
                    .collect(Collectors.toList());

            for (File directory: directories) {
                try {
                    LocalDate yyyyMMdd = LocalDate.parse(directory.getName(), DateTimeFormatter.ofPattern("yyyyMMdd"));
                    if (!lineDate.isBefore(yyyyMMdd))
                        deleteDirectoryRecursivelyByFile(directory);

                } catch (Exception e) {
                    log.error("{} 로그 디렉토리안에 잘못된 형식의 폴더를 삭제했습니다.", e.getMessage());
                    deleteDirectoryRecursivelyByFile(directory);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDirectoryRecursivelyByFile(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            Arrays.stream( files )
                    .sorted(Comparator.comparing(file -> file.isDirectory() ? 1 : -1))
                    .forEach(file -> {
                        if (file.isDirectory()) {
                            deleteDirectoryRecursivelyByFile(file);
                        }
                        else if (file.delete()) {
                            log.debug("삭제 파일: {}", file);
                        }
                    });
            if (directory.delete()) {
                log.debug("삭제 디렉토리: {}", directory);
            }
        }
    }
}
