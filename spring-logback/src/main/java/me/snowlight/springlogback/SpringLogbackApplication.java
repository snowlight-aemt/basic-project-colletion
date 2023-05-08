package me.snowlight.springlogback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Slf4j
public class SpringLogbackApplication {

	public static void main(String[] args) {
//		log.info("info");
//        log.debug("debug");
		SpringApplication.run(SpringLogbackApplication.class, args);
	}

}
