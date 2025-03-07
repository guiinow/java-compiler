package lang.ast.types;

import lang.ast.LVisitor;

public class TyNull extends Type {
    public TyNull(int line, int col) {
        super(line, col, "Null");
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}
