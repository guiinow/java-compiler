package lang.ast.command;

import java.util.List;
import lang.ast.LVisitor;
import lang.ast.expr.Exp;

public class CallCmd extends Cmd {
    
    public String id;
    public List<Exp> exp;
    public List<Exp> lv;

       public CallCmd(int line, int column, String id, List<Exp> exp, List<Exp> lv) {
           super(line, column);
           this.id = id;
           this.exp = exp;
           this.lv = lv;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

    public List<Exp> getReturns() {
        return lv;
    }

    public List<Exp> getArgs() {
        return exp;
    }

}

