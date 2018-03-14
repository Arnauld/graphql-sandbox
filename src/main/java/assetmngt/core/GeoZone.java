package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class GeoZone {
    public static final GeoZone EURO = new GeoZone("EURO", "ZONE EURO");
    public static final GeoZone EUR_HORS_EURO = new GeoZone("EUR_HORS_EURO", "EUROPE HORS ZONE EURO");
    public static final GeoZone DIVERSIF = new GeoZone("DIVERSIF", "DIVERSIFICATION");
    public static final GeoZone USA = new GeoZone("USA", "USA");

    public final String code;
    public final String label;

    public GeoZone(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
