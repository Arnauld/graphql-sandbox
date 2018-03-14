package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Nature {
    public static Nature FR = new Nature("FR", "FRANCE");

    public final String code;
    public final String label;

    public Nature(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
