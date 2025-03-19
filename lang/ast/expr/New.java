package lang.ast.expr;

import lang.ast.types.Type;
import lang.ast.LNode;
import lang.ast.LVisitor;

public class New extends Exp {
    private Type type;
    private Exp exp;

    public New(int line, int column, Type type, Exp exp) {
        super(line, column);
        this.type = type;
        this.exp = exp;
    }

    public Type getType() {
        return type;
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
        return "New(" + type.toString() + ")";
    }
}
