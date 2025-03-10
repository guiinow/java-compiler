package lang.ast.expr;

import lang.ast.LNode;
import lang.ast.LVisitor;

public class LValue extends LNode {
    private String id;

    public LValue(int line, int column, String id) {
        super(line, column);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public void accept(LVisitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString() {
        return "LValue(" + id + ")";
    }
}
