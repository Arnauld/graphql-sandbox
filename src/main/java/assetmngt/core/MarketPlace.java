package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class MarketPlace {
    public static MarketPlace NYSE_PARIS = new MarketPlace("025", "NYSE EN PARIS", Country.FR);

    public final String code;
    public final String label;
    public final Country country;

    public MarketPlace(String marketCode, String marketLabel, Country country) {
        this.code = marketCode;
        this.label = marketLabel;
        this.country = country;
    }
}
