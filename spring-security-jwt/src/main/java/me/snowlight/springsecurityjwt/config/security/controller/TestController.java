package me.snowlight.springsecurityjwt.config.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String test() {
        return "OK";
    }
    @GetMapping("/filter")
    public String test2() {
        return "OK";
    }
}
