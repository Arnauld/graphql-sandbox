package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Sector {
    public static final Sector OPC_DIV = new Sector("OPC_DIV", "OPCVM ET DIVERS");

    public final String code;
    public final String label;

    public Sector(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
