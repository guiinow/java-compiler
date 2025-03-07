package lang.ast.types;

import lang.ast.LNode;
import lang.ast.LVisitor;

public class Type extends LNode {
    private String typeName;

    public Type(int line, int col, String typeName) {
        super(line, col);
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

}
