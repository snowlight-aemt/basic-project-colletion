public class App {
    public static void main(String[] args) {
//          Thread 상속
//        ThreadEx01 threadEx01 = new ThreadEx01();
//        threadEx01.doSomething();

//          Runnable 람다
//        RunnableEx01 runnableEx01 = new RunnableEx01();
//        runnableEx01.doSomething();


//          Callable 람다
//        CallableEx01 callableEx01 = new CallableEx01();
//        callableEx01.doSomething();

//          Callable 람다
//          Executor (Pooling 사용)
        ExecutorEx01 executorEx01 = new ExecutorEx01();
        executorEx01.doSomething();
    }
}
