package me.snowlight.springaopbasic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CallServiceIssueTest {
    CallServiceIssue callServiceIssue;

    @Autowired
    public CallServiceIssueTest(CallServiceIssue service) {
        callServiceIssue = service;
    }

    @DisplayName("external 호출 테스트")
    @Test
    void externalCallTest() {
        callServiceIssue.external();
//        2024-02-17T16:39:58.279+09:00  INFO 27522 --- [    Test worker] m.snowlight.springaopbasic.AspectLogic   : call AOP
//        2024-02-17T16:39:58.279+09:00  INFO 27522 --- [    Test worker] m.snowlight.springaopbasic.CallService   : call external
//        2024-02-17T16:39:58.279+09:00  INFO 27522 --- [    Test worker] m.snowlight.springaopbasic.CallService   : call internal
    }

    @DisplayName("internal 호출 테스트")
    @Test
    void internalCallTest() {
        callServiceIssue.internal();
//        2024-02-17T16:39:58.286+09:00  INFO 27522 --- [    Test worker] m.snowlight.springaopbasic.AspectLogic   : call AOP
//        2024-02-17T16:39:58.286+09:00  INFO 27522 --- [    Test worker] m.snowlight.springaopbasic.CallService   : call internal
    }
}   