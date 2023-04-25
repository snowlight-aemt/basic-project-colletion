package me.snowlight.springtransactionisoration01.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootTest
class TeamServiceTest {
    @Autowired
    private TeamService teamService;
    private Queue<Boolean> queue = new LinkedBlockingQueue<>();

    @Test
    void repeatable_isolation() throws InterruptedException {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        CountDownLatch latch =new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                Team team = null;
                try {
                    boolean insert = teamService.insert();
                    queue.add(insert);
                } finally {
                    latch.countDown();
                }
                return team.getId().toString();
            });
        }

        latch.await();
        executorService.shutdown();

        Assertions.assertThat(queue.size()).isEqualTo(threadCount);
        int size = queue.stream().filter(v -> !v).toList().size();
        Assertions.assertThat(size).isZero();
    }

    @Disabled
    @Test
    void increase() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch =new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                Team team = null;
                try {
                    teamService.increase(44L);
                    team = teamService.retrieveTeam(44L);
                } finally {
                    latch.countDown();
                }
                Assertions.assertThat(team).isNotNull();
                return team.getId().toString();
            });
        }
        latch.await();
        executorService.shutdown();
    }
}