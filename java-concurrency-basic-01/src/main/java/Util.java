import java.time.LocalTime;

public class Util {
    public static void log(String msg) {
        System.out.println(LocalTime.now() + " ("
                + Thread.currentThread().getName() + ") " +  msg);
    }
}
