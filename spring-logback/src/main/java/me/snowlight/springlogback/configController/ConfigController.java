package me.snowlight.springlogback.configController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ConfigController {

    @GetMapping("/v1/config")
    public String config() {
        log.debug("CONFIG INFO");
        log.info("CONFIG INFO");

        return "Success";
    }
}
