package assetmngt.graphql.dto;

import assetmngt.core.Holding;
import assetmngt.core.Instrument;
import assetmngt.core.Model;
import assetmngt.core.Quotation;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Converters {
    public ModelView toView(Model model) {
        return new ModelView(model);
    }
    public HoldingView toView(Holding holding) {
        return new HoldingView(holding);
    }

    public InstrumentView toView(Instrument instrument) {
        return new InstrumentView(instrument);
    }

    public QuotationView toView(Quotation quotation) {
        return new QuotationView(quotation);
    }
}
