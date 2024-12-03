package me.snow.modulegradle.controller;

import me.snow.modulegradle.server.HelloServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelpController {
    @Autowired
    private HelloServer helloServer;

    @GetMapping("/hello")
    public List<String> hello() {

        helloServer.print();

        return List.of("123123123");
    }
}
