package assetmngt.graphql.dto;

import java.util.Map;

public class PagingInput {

    public static PagingInput fromMap(Map<String, Object> map) {
        String after = (String) map.get("after");
        Integer count = (Integer) map.get("count");
        return new PagingInput(after, count == null ? 0 : count);
    }

    public final String after;
    public final int count;

    public PagingInput(String after, int count) {
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

    public int asOffset() {
        if (after != null && after.matches("\\d+"))
            return Integer.parseInt(after);
        return 0;
    }
}
