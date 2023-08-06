package com.example.springsecurity_2_7;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
public class AuthorizationController {
    @GetMapping("/test/1")
    public void testOne() {
        log.info("test 1 ");

    }

    @GetMapping("/test/2")
    public void testTwo() {
        log.info("test 2 ");

    }
}
