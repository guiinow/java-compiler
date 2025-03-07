package lang.ast.command;

import lang.ast.expr.LValue;
import lang.ast.LVisitor;

public class ReadCmd extends Cmd {
    private LValue lvalue;

    public ReadCmd(int line, int column, LValue lvalue) {
        super(line, column);
        this.lvalue = lvalue;
    }

    public LValue getLValue() {
        return lvalue;
    }

    @Override
    public void accept(LVisitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString() {
        return "ReadCmd(" + lvalue + ")";
    }
}
