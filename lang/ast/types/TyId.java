package lang.ast.types;

import lang.ast.LVisitor;

public class TyId extends Type {
    public TyId(int line, int col, String typeName) {
        super(line, col, typeName);
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}
