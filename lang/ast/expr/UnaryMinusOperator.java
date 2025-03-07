package lang.ast.expr;

import lang.ast.LVisitor;

public class UnaryMinusOperator extends UnOp {
    public Exp e;

    public UnaryMinusOperator(int line, int col, Exp e) {
        super(line, col, e);
        this.e = e;
    }

    public Exp getExp(){
        return e;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}
