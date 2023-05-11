import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListFunc {
    public List<String> list = new ArrayList<>();
    public Integer count = 0;

    public int workOne() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        count++;
        String uuid = "one_" + UUID.randomUUID();
        list.add(uuid);
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
        list.add(uuid);
        return count;
    }
}
