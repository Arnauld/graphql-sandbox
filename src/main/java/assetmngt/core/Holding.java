package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Holding {
    public final Isin isin;
    public final Weight weight;
    public final Quantity qty;

    public Holding(Isin isin, Weight weight, Quantity qty) {
        this.isin = isin;
        this.weight = weight;
        this.qty = qty;
    }
}
