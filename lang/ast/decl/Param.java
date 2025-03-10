package lang.ast.decl;

import lang.ast.LNode;
import lang.ast.types.Type;
import lang.ast.LVisitor;

public class Param extends LNode {
    private String id;
    private Type type;

    public Param(int line, int column, String id, Type type) {
        super(line, column);
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void accept(LVisitor v) {
        v.visit(this);
    }
}
