package assetmngt.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class InstrumentsFake implements Instruments {

    private Map<Isin, Instrument> instruments = new HashMap<>();

    public InstrumentsFake() {
        registerInstruments(
                new Instrument(Isin.isin("FR0010148346"), "ETOILE VAL MOY", MarketPlace.NYSE_PARIS, AssetType.ACTION, GeoZone.EURO, Sector.OPC_DIV),
                new Instrument(Isin.isin("FR0011066802"), "OPCIMMO - P", MarketPlace.NYSE_PARIS, AssetType.AUTRES, GeoZone.EUR_HORS_EURO, Sector.OPC_DIV),
                new Instrument(Isin.isin("FR0010233270"), "ETOILE MULTI GESTION MONDE", MarketPlace.NYSE_PARIS, AssetType.ACTION, GeoZone.DIVERSIF, Sector.OPC_DIV),
                new Instrument(Isin.isin("FR0010541425"), "ETOILE MULTI GESTION ETATS-UNI", MarketPlace.NYSE_PARIS, AssetType.ACTION, GeoZone.USA, Sector.OPC_DIV),
                new Instrument(Isin.isin("FR0011482074"), "ETOILE MULTI GESTION FRANCE (C", MarketPlace.NYSE_PARIS, AssetType.ACTION, GeoZone.EURO, Sector.OPC_DIV),
                new Instrument(Isin.isin("FR0010540856"), "ETOILE MUL GEST EURP", MarketPlace.NYSE_PARIS, AssetType.ACTION, GeoZone.EUR_HORS_EURO, Sector.OPC_DIV),
                new Instrument(Isin.isin("FR0012951101"), "ETOILE OBLIG OPPORTUNITES (C)", MarketPlace.NYSE_PARIS, AssetType.OBLIGATION, GeoZone.DIVERSIF, Sector.OPC_DIV),
                new Instrument(Isin.isin("FR0011199371"), "AMUNDI PATRIMOINE", MarketPlace.NYSE_PARIS, AssetType.AUTRES, GeoZone.DIVERSIF, Sector.OPC_DIV),
                new Instrument(Isin.isin("FR0010931147"), "ETOILE CLIQUET 90 - P (C)", MarketPlace.NYSE_PARIS, AssetType.AUTRES, GeoZone.EURO, Sector.OPC_DIV)
        );
    }

    private void registerInstruments(Instrument... instruments) {
        for (Instrument instrument : instruments) {
            this.instruments.put(instrument.isin, instrument);
        }
    }

    @Override
    public Instrument byIsin(Isin isin) {
        return instruments.get(isin);
    }

}
