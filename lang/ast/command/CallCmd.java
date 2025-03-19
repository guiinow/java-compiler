package lang.ast.command;

import java.util.List;
import lang.ast.LVisitor;
import lang.ast.expr.Exp;
import lang.ast.expr.LValue;

public class CallCmd extends Cmd {
    
    public String id;
    public List<Exp> exp;
    public LValue lv;

       public CallCmd(int line, int column, String id, List<Exp> exp, LValue lv) {
           super(line, column);
           this.id = id;
           this.exp = exp;
           this.lv = lv;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

}

