package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Country {
    public static Country FR = new Country("FR", "FRANCE");

    public final String code;
    public final String label;

    public Country(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
