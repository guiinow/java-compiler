package lang.ast.expr;

import lang.ast.LVisitor;

public class NullLit extends Exp {
    private Object value;

    public NullLit(int line, int col, Object value) {
        super(line, col);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}
