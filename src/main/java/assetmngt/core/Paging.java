package assetmngt.core;

import java.util.List;

public class Paging {

    private final int offset;
    private final int count;

    public Paging(int offset, int count) {
        this.offset = offset;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "offset='" + offset + '\'' +
                ", count=" + count +
                '}';
    }

    public <T> List<T> apply(List<T> xs) {
        int min = Math.min(offset, xs.size());
        int max = Math.min(offset + count, xs.size());
        return xs.subList(min, max);
    }
}
