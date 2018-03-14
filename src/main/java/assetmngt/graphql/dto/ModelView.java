package assetmngt.graphql.dto;

import assetmngt.core.Model;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class ModelView extends BaseView<Model> {

    public ModelView(Model model) {
        super(model);
    }

    public String getCode() {
        return __source.code;
    }

    public String getLabel() {
        return __source.label;
    }

    public CodeLabel getStructure() {
        return new CodeLabel(__source.structure.code, __source.structure.label);
    }

    public CodeLabel getNature() {
        return new CodeLabel(__source.nature.code, __source.nature.label);
    }

    public CodeLabel getUse() {
        return new CodeLabel(__source.use.code, __source.use.label);
    }

    public boolean isActive() {
        return __source.active;
    }
}
