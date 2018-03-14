package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Instrument {

    public final Isin isin;
    public final String label;
    public final MarketPlace marketPlace;
    public final AssetType assetType;
    public final GeoZone geoZone;
    public final Sector sector;

    public Instrument(Isin isin, String label, MarketPlace marketPlace, AssetType assetType, GeoZone geoZone, Sector sector) {
        this.isin = isin;
        this.label = label;
        this.marketPlace = marketPlace;
        this.assetType = assetType;
        this.geoZone = geoZone;
        this.sector = sector;
    }
}
