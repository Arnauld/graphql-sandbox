package assetmngt.core;

import java.util.Objects;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Isin {
    public final String raw;

    public static Isin isin(String raw) {
        Objects.requireNonNull(raw);
        return new Isin(raw);
    }

    private Isin(String raw) {
        this.raw = raw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Isin isin = (Isin) o;

        return raw.equals(isin.raw);
    }

    @Override
    public int hashCode() {
        return raw.hashCode();
    }
}
