package assetmngt.graphql;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class GraphQLContext {
    private Map<Object, Object> data = new HashMap<>();

    public void declare(Object key, Object value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key) {
        return (T) data.get(key);
    }
}
