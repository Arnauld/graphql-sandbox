package assetmngt.core;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Model {
    public final String code;
    public final String label;
    public final Structure structure;
    public final Nature nature;
    public final Use use;
    public final boolean active;

    public Model(String code, String label, Structure structure, Nature nature, Use use, boolean active) {
        this.code = code;
        this.label = label;
        this.structure = structure;
        this.nature = nature;
        this.use = use;
        this.active = active;
    }
}
