package lang.ast.decl;

import java.util.List;

import lang.ast.LNode;
import lang.ast.LVisitor;
import lang.ast.command.Cmd;

public class Block extends LNode {
    private List<Cmd> commands;

    public Block(int line, int col, List<Cmd> commands) {
        super(line, col);
        this.commands = commands;
    }

    public List<Cmd> getCommands() {
        return commands;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}