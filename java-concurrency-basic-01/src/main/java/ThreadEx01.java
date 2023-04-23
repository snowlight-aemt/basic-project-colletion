public class ThreadEx01 {
//          결과
//        20:56:13.439873 (main) Anonymous Thread
//        20:56:13.449520 (main) doSomeThing Start
//        20:56:13.449622 (main) doSomeThing end
//        20:56:13.449697 (Thread-0) (Anonymous) Thread is Running
//        20:56:14.454980 (Thread-0) (Anonymous) Finish end()

    public void doSomething() {
        Util.log("Anonymous Thread");
        Thread anonymousClass = new Thread(() -> {
                Util.log("(Anonymous) Thread is Running");
                try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
                Util.log("(Anonymous) Finish end()");
        });

        Util.log("doSomeThing Start");
        anonymousClass.start();
        Util.log("doSomeThing end");
    }

}
