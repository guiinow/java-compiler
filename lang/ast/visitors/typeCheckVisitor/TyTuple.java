package lang.ast.visitors.typeCheckVisitor;

import java.util.List;

public class TyTuple extends VType {

    public List<VType> types;
    private TyTuple(List<VType> types) {
        super(CLTypes.TUPLE);
    }

    public List<VType> getTypes() {
        return types;
    }

    public boolean match(VType t) {
        boolean r = getTypeValue() == t.getTypeValue();
        r = r && ((TyTuple) t).getTypes().size() == types.size();
        if (r) {
            for (int i = 0; i < types.size(); i++) {
                r = r && types.get(i).match(((TyTuple) t).getTypes().get(i));
            }
        }

        return r;
    }

    @Override
    public String toString() {
        return "int";
    }
}
