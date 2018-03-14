package assetmngt.graphql.dto;

import assetmngt.core.Instrument;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class InstrumentView extends BaseView<Instrument> {
    public InstrumentView(Instrument instrument) {
        super(instrument);
    }

    public String getIsin() {
        return __source.isin.raw;
    }

    public String getLabel() {
        return __source.label;
    }

    public MarketPlaceView getMarketPlaceView() {
        return new MarketPlaceView(
                __source.marketPlace.code,
                __source.marketPlace.label,
                __source.marketPlace.country.code,
                __source.marketPlace.country.label);
    }

    public CodeLabel getAssetType() {
        return new CodeLabel(__source.assetType.code, __source.assetType.label);
    }

    public CodeLabel getGeoZone() {
        return new CodeLabel(__source.geoZone.code, __source.geoZone.label);
    }

    public CodeLabel getSector() {
        return new CodeLabel(__source.sector.code, __source.sector.label);
    }
}
