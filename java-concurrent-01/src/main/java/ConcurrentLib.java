import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentLib {
    public ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
    public Integer count = 0;

    public int workOne() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        count++;
        String uuid = "one_" + UUID.randomUUID();
        concurrentMap.put(uuid, count);
        return count;
    }

    public int workTwo() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        count++;
        String uuid = "two_" + UUID.randomUUID();
        concurrentMap.put(uuid, count);
        return count;
    }
}
