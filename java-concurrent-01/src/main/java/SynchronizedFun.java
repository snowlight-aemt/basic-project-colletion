import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SynchronizedFun {
    public Map<String, Integer> map = new HashMap<>();
    public Integer count = 0;

    public synchronized int workOne() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        count++;
        String uuid = "one_" + UUID.randomUUID();
        map.put(uuid, count);
        return count;
    }

    public synchronized int workTwo() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        count++;
        String uuid = "two_" + UUID.randomUUID();
        map.put(uuid, count);
        return count;
    }
}
