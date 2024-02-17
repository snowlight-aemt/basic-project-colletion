package me.snowlight.springaopbasic;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class AspectLogic {
    @Before("execution(* me.snowlight.springaopbasic.*..*(..))")
    public void beforeLog() {
        log.info("call AOP");
    }
}
