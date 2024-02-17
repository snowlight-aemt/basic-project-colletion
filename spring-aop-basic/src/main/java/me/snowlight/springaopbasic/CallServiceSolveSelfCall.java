package me.snowlight.springaopbasic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceSolveSelfCall {
    private CallServiceSolveSelfCall service;

    @Autowired
    public void setCallService(CallServiceSolveSelfCall service) {
        this.service = service;
    }

    public void external() {
        log.info("call external");
        this.service.internal();
//        internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
