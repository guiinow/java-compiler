package lang.ast.expr;



import java.util.List;
import lang.ast.LVisitor;

public class CallExp extends Exp {
    
    public String id;
    public List<Exp> exp;
    public Exp retIndex;

       public CallExp(int line, int column, String id, List<Exp> exp, Exp retIndex) {
           super(line, column);
           this.id = id;
           this.exp = exp;
           this.retIndex = retIndex;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

}

