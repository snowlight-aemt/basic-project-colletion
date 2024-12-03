package me.snow.modulegradle.server;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class HelloServerTest {
//    public static final int THREAD_CNT = 10000;
    public static final int THREAD_CNT = 10000;
    @Autowired
    private HelloServer helloServer;


    @RepeatedTest(5)
    public void testIncrease() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_CNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_CNT);


        for (int i = 0; i < THREAD_CNT; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(10);
                    helloServer.increase("0101");
                } catch (Exception e) {
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        System.out.println("끝 --");
    }

    public int getRandomNumber(int min, int max) {
        System.out.println();
        return (int) ((Math.random() * (max - min)) + min);
    }
}