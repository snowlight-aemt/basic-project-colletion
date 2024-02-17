package me.snowlight.springaopbasic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceSolveRate {
    private ObjectProvider<CallServiceSolveRate> service;

    @Autowired
    public void setCallService(ObjectProvider<CallServiceSolveRate> service) {
        this.service = service;
    }

    public void external() {
        log.info("call external");
        CallServiceSolveRate callServiceSolveRate = this.service.getObject();
        callServiceSolveRate.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
