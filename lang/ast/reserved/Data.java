package lang.ast.reserved;

import java.util.List;

import lang.ast.LNode;
import lang.ast.LVisitor;
import lang.ast.decl.Decl;;

public class Data extends LNode {
    private final String typeName;
    private final List<Decl> fields;

    public Data(int line, int col, String typeName, List<Decl> fields) {
        super(line, col);
        this.typeName = typeName;
        this.fields = fields;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<Decl> getFields() {
        return fields;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}
