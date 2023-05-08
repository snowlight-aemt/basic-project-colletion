package me.snowlight.springlogback;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class LogController {

    @GetMapping("/")
    public String log() {
        MDC.put("id", "A");
        log.info(LocalDateTime.now().toString());
        log.info("info A");
        log.debug("debug");
        MDC.clear();

        return "OK A";
    }

    @GetMapping("/b")
    public String logB() {
        MDC.put("id", "B");
        log.info(LocalDateTime.now().toString());
        log.info("info B");
        log.debug("debug");
        MDC.clear();
        return "OK B";
    }

}
