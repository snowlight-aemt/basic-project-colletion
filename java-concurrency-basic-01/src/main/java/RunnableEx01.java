public class RunnableEx01 {
//          결과
//        20:55:46.755856 (main) Extends Thread
//        20:55:46.765497 (main) doSomeThing Start
//        20:55:46.765601 (main) doSomeThing end
//        20:55:46.765680 (Thread-0) (Extends Thread) Thread is Running
//        20:55:47.770973 (Thread-0) (Extends Thread) Finish end()

    public static class MyThread extends Thread {
        @Override
        public void run() {
            Util.log("(Extends Thread) Thread is Running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Util.log("(Extends Thread) Finish end()");
        }
    }

    public void doSomething() {
        Util.log("Extends Thread");
        Thread myThread = new MyThread();
        Util.log("doSomeThing Start");
        myThread.start();
        Util.log("doSomeThing end");
    }
}
