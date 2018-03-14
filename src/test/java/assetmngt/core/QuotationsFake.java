package assetmngt.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class QuotationsFake implements Quotations {

    private Map<Isin, Quotation> quotations = new HashMap<>();

    public QuotationsFake() {
        registerQuotations(
                new Quotation(Isin.isin("FR0010148346"), BigDecimal.valueOf(27.39), Currency.EUR),
                new Quotation(Isin.isin("FR0011066802"), BigDecimal.valueOf(115.52432), Currency.EUR),
                new Quotation(Isin.isin("FR0010233270"), BigDecimal.valueOf(1718.06), Currency.EUR),
                new Quotation(Isin.isin("FR0010541425"), BigDecimal.valueOf(113.86), Currency.EUR),
                new Quotation(Isin.isin("FR0011482074"), BigDecimal.valueOf(147.91), Currency.EUR),
                new Quotation(Isin.isin("FR0010540856"), BigDecimal.valueOf(1140.45), Currency.EUR),
                new Quotation(Isin.isin("FR0012951101"), BigDecimal.valueOf(85.21), Currency.EUR),
                new Quotation(Isin.isin("FR0011199371"), BigDecimal.valueOf(115.17), Currency.EUR),
                new Quotation(Isin.isin("FR0010931147"), BigDecimal.valueOf(108.41), Currency.EUR)
        );
    }

    private void registerQuotations(Quotation... quotations) {
        for (Quotation quotation : quotations) {
            this.quotations.put(quotation.isin, quotation);
        }
    }

    @Override
    public Quotation byIsin(Isin isin) {
        return quotations.get(isin);
    }
}
