package me.snowlight.springlogback.helloController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @GetMapping("/v1/hello")
    public String hello() {
        log.debug("HELLO INFO");
        log.info("HELLO INFO");

        return "Success";
    }
}
