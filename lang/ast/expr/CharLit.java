package lang.ast.expr;

import lang.ast.LVisitor;

public class CharLit extends Exp {
    public Character value;

    public CharLit(int line, int col, Character value) {
        super(line, col);
        this.value = value;
    }

    public Character getValue() {
        return value;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

}
