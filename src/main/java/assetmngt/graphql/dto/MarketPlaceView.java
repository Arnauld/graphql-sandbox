package assetmngt.graphql.dto;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class MarketPlaceView {
    public final String marketCode;
    public final String marketLabel;
    public final String countryCode;
    public final String countryLabel;

    public MarketPlaceView(String marketCode, String marketLabel, String countryCode, String countryLabel) {

        this.marketCode = marketCode;
        this.marketLabel = marketLabel;
        this.countryCode = countryCode;
        this.countryLabel = countryLabel;
    }
}
