package lang.ast.decl;

import lang.ast.expr.Exp;
import lang.ast.LVisitor;
import lang.ast.expr.LValue;

public class ArrayAccess extends LValue {
    private LValue base;
    private Exp index;

    public ArrayAccess(int line, int column, LValue base, Exp index) {
        // Pode utilizar os dados do lvalue base
        super(line, column, base.getId());
        this.base = base;
        this.index = index;
    }

    public LValue getBase() {
        return base;
    }

    public Exp getIndex() {
        return index;
    }

    @Override
    public void accept(LVisitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString() {
        return "ArrayAccess(" + base + "[" + index + "])";
    }
}
