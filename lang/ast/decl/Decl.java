package lang.ast.decl;

import lang.ast.LNode;
import lang.ast.LVisitor;
import lang.ast.types.Type;

public class Decl extends LNode {
    private String fieldName;
    private Type fieldType;

    public Decl(int line, int col, String fieldName, Type fieldType) {
        super(line, col);
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Type getFieldType() {
        return fieldType;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

}
