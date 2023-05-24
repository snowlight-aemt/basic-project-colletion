package me.snowlight.springvalid;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ValidController {
    @PostMapping("/hello")
    public ResponseEntity<?> hello(@Valid @RequestBody TestDto testDto) {
        log.info("hello");
        return ResponseEntity.ok("OK");
    }
}
