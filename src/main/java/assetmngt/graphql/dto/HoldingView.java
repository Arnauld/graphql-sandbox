package assetmngt.graphql.dto;

import assetmngt.core.Holding;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class HoldingView extends BaseView<Holding> {

    public HoldingView(Holding holding) {
        super(holding);
    }

    public double getWeight() {
        return __source.weight.toDouble();
    }

    public double getQty() {
        return __source.qty.toDouble();
    }
}
