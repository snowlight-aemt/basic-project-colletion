import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MapFunc {
    public Map<String, Integer> map = new HashMap<>();
    public Integer count = 0;

    public int workOne() {
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

    public int workTwo() {
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
