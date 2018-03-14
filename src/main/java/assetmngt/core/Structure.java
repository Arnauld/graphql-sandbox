package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Structure {
    public static Structure FR = new Structure("FR", "FRANCE");

    public final String code;
    public final String label;

    public Structure(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
