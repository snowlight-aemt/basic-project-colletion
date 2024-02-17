package me.snowlight.springaopbasic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CallServiceIssueSolveRateTest {
    CallServiceSolveRate callService;

    @Autowired
    public CallServiceIssueSolveRateTest(CallServiceSolveRate service) {
        callService = service;
    }

    @DisplayName("external 호출 테스트")
    @Test
    void externalCallTest() {
        callService.external();
//        2024-02-17T16:46:00.282+09:00  INFO 29122 --- [    Test worker] m.snowlight.springaopbasic.AspectLogic   : call AOP
//        2024-02-17T16:46:00.282+09:00  INFO 29122 --- [    Test worker] m.s.springaopbasic.CallServiceSolveRate  : call external
//        2024-02-17T16:46:00.282+09:00  INFO 29122 --- [    Test worker] m.snowlight.springaopbasic.AspectLogic   : call AOP
//        2024-02-17T16:46:00.282+09:00  INFO 29122 --- [    Test worker] m.s.springaopbasic.CallServiceSolveRate  : call internal
    }

    @DisplayName("internal 호출 테스트")
    @Test
    void internalCallTest() {
        callService.internal();
//        2024-02-17T16:46:00.289+09:00  INFO 29122 --- [    Test worker] m.snowlight.springaopbasic.AspectLogic   : call AOP
//        2024-02-17T16:46:00.289+09:00  INFO 29122 --- [    Test worker] m.s.springaopbasic.CallServiceSolveRate  : call internal
    }
}