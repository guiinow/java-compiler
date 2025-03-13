package lang.ast.expr;

import lang.ast.LVisitor;

public class LoopCond extends Exp{
    private Exp exp;
    
    public LoopCond(int line, int col, Exp exp) {
        super(line, col);
        this.exp = exp;
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
        return "LoopCond(" + exp + ")";
    }
    
}