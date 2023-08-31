package me.snowlight.springswaggerspringdocv1.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Data
    public static class Reqest {
        private String TEST;
        private String HELLO;
    }

    @PostMapping("/v1/post")
    public String hello(@RequestBody Reqest reqest) {
        return "success";
    }

}
