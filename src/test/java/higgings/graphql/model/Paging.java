package higgings.graphql.model;

import java.util.List;
import java.util.Map;

public class Paging {

    public static Paging fromMap(Map<String, Object> map) {
        String after = (String) map.get("after");
        Integer count = (Integer) map.get("count");
        return new Paging(after, count == null ? 0 : count);
    }

    private final String after;
    private final int count;

    public Paging(String after, int count) {
        this.after = after;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "after='" + after + '\'' +
                ", count=" + count +
                '}';
    }

    public <T> List<T> apply(List<T> xs) {
        int min = Math.min(toInt(after), xs.size());
        int max = Math.min(toInt(after) + count, xs.size());
        return xs.subList(min, max);
    }

    private int toInt(String s) {
        if (s.matches("\\d+"))
            return Integer.parseInt(s);
        return 0;
    }
}
