package assetmngt.graphql.dto;

import assetmngt.core.Quotation;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class QuotationView extends BaseView<Quotation> {

    protected QuotationView(Quotation quotation) {
        super(quotation);
    }

    public double getValue() {
        return __source.amount.doubleValue();
    }

    public String getCurrency() {
        return __source.currency.name();
    }

}
