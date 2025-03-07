package lang.ast.types;

import lang.ast.LVisitor;

public class TyChar extends Type {
    public TyChar(int line, int col) {
        super(line, col, "Char");
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

}
