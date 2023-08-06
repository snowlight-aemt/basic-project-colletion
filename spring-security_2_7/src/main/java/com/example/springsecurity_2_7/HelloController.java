package com.example.springsecurity_2_7;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
public class HelloController {
    @PostMapping("/hello")
    public ResponseEntity<HelloResponseDto> hello(HelloRequestDto request) {
        log.info("POST /hello");
        return ResponseEntity.status(HttpStatus.OK).body(new HelloResponseDto());
    }

    @GetMapping("/hello")
    public ResponseEntity<HelloResponseDto> hello() {
        log.info("GET /hello");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getPrincipal().toString());
        authentication.getAuthorities().stream().forEach((v) -> {
            log.info(v.getAuthority());
        });

        return ResponseEntity.status(HttpStatus.OK).body(new HelloResponseDto());
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public class HelloResponseDto {
        private String hello;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public class HelloRequestDto {
        private String hello;
    }
}
