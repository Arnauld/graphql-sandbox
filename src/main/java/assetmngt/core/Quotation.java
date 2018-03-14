package assetmngt.core;

import java.math.BigDecimal;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Quotation {

    public final Isin isin;
    public final BigDecimal amount;
    public final Currency currency;

    public Quotation(Isin isin, BigDecimal amount, Currency currency) {

        this.isin = isin;
        this.amount = amount;
        this.currency = currency;
    }
}
