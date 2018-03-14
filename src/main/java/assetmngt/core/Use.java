package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Use {
    public static Use FR = new Use("FR", "FRANCE");

    public final String code;
    public final String label;

    public Use(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
