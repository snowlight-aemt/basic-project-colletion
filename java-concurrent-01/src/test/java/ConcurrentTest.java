import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentTest {
    @DisplayName("일반 카운트 실패")
    @RepeatedTest(10)
    void map_fail() throws InterruptedException {
        int count = 10000;
        CountDownLatch latch = new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        MapFunc func = new MapFunc();
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    String name = Thread.currentThread().getName();
                    func.workOne();
                    func.workTwo();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(count * 2, func.map.size())
        );
    }

    @DisplayName("Concurrent 라이브러리 카운트 성공")
    @RepeatedTest(10)
    void concurrent_success() throws InterruptedException {
        int count = 100;
        CountDownLatch latch = new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        ConcurrentLib func = new ConcurrentLib();
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    String name = Thread.currentThread().getName();
                    func.workOne();
                    func.workTwo();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Assertions.assertAll(
                () -> Assertions.assertEquals(count * 2, func.concurrentMap.size())
        );
    }

    @DisplayName("Synchronized 예약어 카운트 성공")
    @RepeatedTest(10)
    void synchronized_success() throws InterruptedException {
        int count = 100;
        CountDownLatch latch = new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        SynchronizedFun func = new SynchronizedFun();
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    String name = Thread.currentThread().getName();
                    func.workOne();
                    func.workTwo();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Assertions.assertAll(
                () -> Assertions.assertEquals(count * 2, func.map.size()),
                () -> Assertions.assertEquals(count * 2, func.count)
        );
    }


    @DisplayName("Array add 실패")
    @RepeatedTest(10)
    void list_failed() throws InterruptedException {
        int count = 1000;
        CountDownLatch latch = new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        ListFunc func = new ListFunc();
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    String name = Thread.currentThread().getName();
                    func.workOne();
                    func.workTwo();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Assertions.assertAll(
                () -> Assertions.assertEquals(count * 2, func.list.size()),
                () -> Assertions.assertEquals(count * 2, func.count)
        );
    }
}
