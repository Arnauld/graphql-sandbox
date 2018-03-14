package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class AssetType {
    public static final AssetType ACTION = new AssetType("ACT", "ACTIONS");
    public static final AssetType AUTRES = new AssetType("AUT", "AUTRES");
    public static final AssetType OBLIGATION = new AssetType("OBL", "OBLIGATION");

    public final String code;
    public final String label;

    public AssetType(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
