package assetmngt.graphql.dto;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public abstract class BaseView<T> {
    public final T __source;

    protected BaseView(T source) {
        __source = source;
    }
}
