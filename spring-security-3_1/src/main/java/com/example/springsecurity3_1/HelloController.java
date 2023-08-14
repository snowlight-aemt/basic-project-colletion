package com.example.springsecurity3_1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        log.info(" == helrlo == ");
        return "Success";
    }

    @GetMapping("/auth/not-secured")
    public String fail() {
        return "fail";
    }
}
