package com.sanhait.springasync.controller;

import com.sanhait.springasync.service.CheckinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InHouseController {
    private final CheckinService checkinService;

    @PostMapping("/v1/checkin")
    public String checkin() {
        log.info("checkin v1 체크인");
        checkinService.checkin();

        log.info("checkin v1 체크인 완료");
        return "OK : ";
    }

    @PostMapping("/v2/checkin")
    public CompletableFuture<String> checkinV2() {
        log.info("checkin v2 체크인");
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            // 비동기적으로 처리할 작업

            try {
                log.info("비동기 시작");
                Thread.sleep(3000);
                log.info("비동기 끝");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "CHECK 비동기";
        }).thenApply(result -> {
            // 첫 번째 작업의 결과를 받아 추가 작업을 수행
            return "OK V2 : " + result;
        });
        
        log.info("checkin v2 체크인 완료");
        return stringCompletableFuture;
    }
}
