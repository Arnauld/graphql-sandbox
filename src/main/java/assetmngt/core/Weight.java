package assetmngt.core;

import java.math.BigDecimal;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Weight {
    private final BigDecimal raw;

    public Weight(BigDecimal raw) {
        this.raw = raw;
    }

    public static Weight of(double raw) {
        return new Weight(BigDecimal.valueOf(raw));
    }

    public double toDouble() {
        return raw.doubleValue();
    }
}
