package com.example.springfilterbasic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class InitController {
    @GetMapping("/get")
    public ResponseEntity<String> getMethod(@RequestHeader("api_key") String apiKey, @RequestParam("id") Long id) {
        log.info("get headaer " + apiKey);
        log.info("get id " + id);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/post")
    public ResponseEntity<String> postMethod() {
        log.info("get request");
        return ResponseEntity.ok("OK");
    }
}
