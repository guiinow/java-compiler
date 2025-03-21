package lang.ast.types;

import lang.ast.LVisitor;

public class TyBool extends Type {
    public TyBool(int line, int col) {
        super(line, col, "Bool");
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}
