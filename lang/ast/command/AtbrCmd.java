package lang.ast.command;

import lang.ast.expr.Exp;
import lang.ast.expr.LValue;
import lang.ast.LVisitor;

public class AtbrCmd extends Cmd {
    private LValue lvalue;
    private Exp exp;

    public AtbrCmd(int line, int column, LValue lvalue, Exp exp) {
        super(line, column);
        this.lvalue = lvalue;
        this.exp = exp;
    }

    public LValue getLValue() {
        return lvalue;
    }

    public Exp getExp() {
        return exp;
    }

    @Override
    public void accept(LVisitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString() {
        return "AtbrCmd(" + lvalue + ", " + exp + ")";
    }
}
