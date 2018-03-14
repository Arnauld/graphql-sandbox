package assetmngt.core;

import java.math.BigDecimal;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Quantity {
    private final BigDecimal raw;

    public Quantity(BigDecimal raw) {
        this.raw = raw;
    }

    public static Quantity of(double raw) {
        return new Quantity(BigDecimal.valueOf(raw));
    }

    public double toDouble() {
        return raw.doubleValue();
    }
}
