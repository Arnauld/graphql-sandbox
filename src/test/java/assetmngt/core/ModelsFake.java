package assetmngt.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static assetmngt.core.Isin.isin;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class ModelsFake implements Models {

    private List<Model> models = new ArrayList<>();

    public ModelsFake() {
        this.models.add(
                new Model("MODELEGCCCPASV",
                        "MODELE GC CCP ASV",
                        new Structure("ASV", "ASV"),
                        new Nature("ASV", "Portefeuille Assurance-vie"),
                        new Use("PRIVATE_BANKING", "Conseiller Private Banking"),
                        true));
    }

    @Override
    public Model byCode(String modelCode) {
        return this.models.stream().filter(m -> m.code.equals(modelCode)).findFirst().orElse(null);
    }

    @Override
    public List<Holding> holdingsOf(String code, Paging paging) {
        List<Holding> holdings = Arrays.asList(
                new Holding(isin("FR0010148346"), Weight.of(0.1581961981), Quantity.of(606442.0)),
                new Holding(isin("FR0011066802"), Weight.of(0.0506639366), Quantity.of(46048.0)),
                new Holding(isin("FR0010233270"), Weight.of(0.1186781431), Quantity.of(7253.0)),
                new Holding(isin("FR0010541425"), Weight.of(0.0601598457), Quantity.of(55478.0)),
                new Holding(isin("FR0011482074"), Weight.of(0.0301020778), Quantity.of(21369.0)),
                new Holding(isin("FR0010540856"), Weight.of(0.219468066), Quantity.of(20206.0)),
                new Holding(isin("FR0012951101"), Weight.of(0.2019617504), Quantity.of(248865.0)),
                new Holding(isin("FR0011199371"), Weight.of(0.100310712), Quantity.of(91452.0)),
                new Holding(isin("FR0010931147"), Weight.of(0.0604592703), Quantity.of(58557.0))
        );
        return paging.apply(holdings);
    }
}
