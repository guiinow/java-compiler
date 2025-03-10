package lang.ast.command;

import lang.ast.expr.Exp;
import lang.ast.LVisitor;
import lang.ast.decl.StmtBlock;

public class If extends Cmd {
    private Exp condition;
    private StmtBlock then;

    public If(int line, int column, Exp condition, StmtBlock then) {
        super(line, column);
        this.condition = condition;
        this.then = then;
    }

    public Exp getCondition() {
        return condition;
    }

    public StmtBlock getThen() {
        return then;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }

    public String toString() {
        return "If(" + condition + ", " + then + ")";
    }
}
