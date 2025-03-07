package lang.ast.expr;

import lang.ast.LVisitor;

public class NotOperator extends UnOp {
    public NotOperator(int line, int col, Exp er) {
        super(line, col, er);
    }

    public String toString() {
        return "!";
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

}
