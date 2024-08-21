package com.sanhait.springasync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Async
@Component
public class CheckinService {

    public void checkin() {
        log.info("체크인 시작");

        try {
            Thread.sleep(3000);
            System.out.println("체크인 중");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("체크인 완료");
    }
}
