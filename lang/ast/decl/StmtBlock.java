package lang.ast.decl;

import java.util.ArrayList;
import java.util.List;

import lang.ast.LNode;
import lang.ast.LVisitor;

public class StmtBlock extends LNode {
    private List<LNode> body;

    public StmtBlock(int l, int c, List<? extends LNode> body) {
        super(l, c);
        this.body = new ArrayList<>(body);
    }

    public List<LNode> getBody() {
        return body;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}
