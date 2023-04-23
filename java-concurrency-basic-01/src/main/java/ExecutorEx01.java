import java.util.concurrent.*;

public class ExecutorEx01 {
//          결과
//        20:55:08.721321 (main) doSomething Start
//        20:55:08.733289 (main) doSomething End
//        20:55:08.733484 (pool-1-thread-1) Threading...
//        20:55:08.734348 (main) Result Value : Thread - pool-1-thread-1

    public void doSomething() {
        // 쓰레드 풀을 사용하기 위해서 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                Util.log("Threading...");
                return "Result Value : Thread - " + Thread.currentThread().getName();
            }
        };

        Util.log("doSomething Start");
        Future<String> future = executorService.submit(callable);
        Util.log("doSomething End");

        try {
            String result = future.get();
            Util.log(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
    }
}
