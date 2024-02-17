package me.snowlight.springaopbasic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CallServiceSolveSelfCallTestIssue {
    CallServiceSolveSelfCall callService;

    @Autowired
    public CallServiceSolveSelfCallTestIssue(CallServiceSolveSelfCall service) {
        callService = service;
    }

    @DisplayName("external 호출 테스트")
    @Test
    void externalCallTest() {
        callService.external();
//        2024-02-17T16:42:30.583+09:00  INFO 28144 --- [    Test worker] m.snowlight.springaopbasic.AspectLogic   : call AOP
//        2024-02-17T16:42:30.583+09:00  INFO 28144 --- [    Test worker] m.s.springaopbasic.CallServiceSelfCall   : call external
//        2024-02-17T16:42:30.583+09:00  INFO 28144 --- [    Test worker] m.snowlight.springaopbasic.AspectLogic   : call AOP
//        2024-02-17T16:42:30.583+09:00  INFO 28144 --- [    Test worker] m.s.springaopbasic.CallServiceSelfCall   : call internal

    }

    @DisplayName("internal 호출 테스트")
    @Test
    void internalCallTest() {
        callService.internal();
//        2024-02-17T16:39:58.286+09:00  INFO 27522 --- [    Test worker] m.snowlight.springaopbasic.AspectLogic   : call AOP
//        2024-02-17T16:39:58.286+09:00  INFO 27522 --- [    Test worker] m.snowlight.springaopbasic.CallService   : call internal
    }
}   