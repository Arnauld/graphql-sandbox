package assetmngt.core;

import java.util.List;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public interface Models {
    Model byCode(String modelCode);

    List<Holding> holdingsOf(String code, Paging paging);
}
