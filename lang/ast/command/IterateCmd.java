package lang.ast.command;

import lang.ast.LoopCond;
import lang.ast.LVisitor;

public class IterateCmd extends Cmd {
    private LoopCond loopCond;
    private Cmd body;

    public IterateCmd(int line, int column, LoopCond loopCond, Cmd body) {
        super(line, column);
        this.loopCond = loopCond;
        this.body = body;
    }

    public LoopCond getLoopCond() {
        return loopCond;
    }

    public Cmd getBody() {
        return body;
    }

    @Override
    public void accept(LVisitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString() {
        return "IterateCmd(" + loopCond + ", " + body + ")";
    }
}