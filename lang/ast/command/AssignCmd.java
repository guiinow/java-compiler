

package lang.ast.command;

import lang.ast.LVisitor;
import lang.ast.expr.Exp;
import lang.ast.expr.LValue;

public class AssignCmd extends Cmd {
    private final LValue lvalue;
    private final Exp exp;

    public AssignCmd(int line, int column, LValue lvalue, Exp exp) {
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
    

   public LValue getVar() {
      return lvalue;
   }

    @Override
    public void accept(LVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return lvalue + " = " + exp + ";";
    }
}