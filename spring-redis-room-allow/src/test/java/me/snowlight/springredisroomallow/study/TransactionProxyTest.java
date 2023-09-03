package me.snowlight.springredisroomallow.study;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class TransactionProxyTest {
    @Autowired CallService callService;

    @Test
    void print_no_transaction() {
        boolean print = this.callService.printTxInfo();
        Assertions.assertThat(print).isFalse();
    }

    @Test
    void external_no_transaction() {
        boolean external = this.callService.external();
        Assertions.assertThat(external).isFalse();
    }

    @Test
    void internal_transaction() {
        boolean internal = this.callService.internal();
        Assertions.assertThat(internal).isTrue();
    }

    @TestConfiguration
    static class Config {
        @Bean
        public CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService {
        public boolean external() {
            log.info("call external");
            printTxInfo();
            return internal();
        }

        @Transactional
        public boolean internal() {
            log.info("call internal");
            return printTxInfo();
        }

        private boolean printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
            return txActive;
        }
    }

}
