package lang.ast.command;

import lang.ast.LNode;
import lang.ast.LVisitor;

public class Cmd extends LNode {
    public Cmd(int line, int column) {
        super(line, column);
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}