import java.util.concurrent.*;

public class CallableEx01 {
//          결과
//        20:55:08.721321 (main) doSomething Start
//        20:55:08.733289 (main) doSomething End
//        20:55:08.733484 (pool-1-thread-1) Threading...
//        20:55:08.734348 (main) Result Value : Thread - pool-1-thread-1

    public void doSomething() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                Util.log("Callable Threading...");
                return "Result Value : Thread - " + Thread.currentThread().getName();
            }
        };

        FutureTask<String> futureTask = new FutureTask(callable);
        Thread thread = new Thread(futureTask);

        Util.log("doSomething Start");
        thread.start();
        Util.log("doSomething End");

        try {
            String result = futureTask.get();
            Util.log(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
