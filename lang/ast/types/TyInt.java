package lang.ast.types;

import lang.ast.LVisitor;

public class TyInt extends Type {
    public TyInt(int line, int col) {
        super(line, col, "Int");
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

}
