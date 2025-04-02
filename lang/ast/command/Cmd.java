package lang.ast.command;

import lang.ast.LNode;

public abstract class Cmd extends LNode {
    public Cmd(int line, int column) {
        super(line, column);
    }
    
}