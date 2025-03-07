package lang.ast.command;

import lang.ast.expr.Exp;
import lang.ast.LVisitor;
import lang.ast.decl.StmtBlock;

public class IfElse extends Cmd {
    private Exp condition;
    private StmtBlock thenStmtBlock;
    private StmtBlock elseStmtBlock;

    public IfElse(int line, int column, Exp condition, StmtBlock thenStmtBlock, StmtBlock elseStmtBlock) {
        super(line, column);
        this.condition = condition;
        this.thenStmtBlock = thenStmtBlock;
        this.elseStmtBlock = elseStmtBlock;
    }

    public Exp getCondition() {
        return condition;
    }

    public StmtBlock getThenStmtBlock() {
        return thenStmtBlock;
    }

    public StmtBlock getElseStmtBlock() {
        return elseStmtBlock;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
    
    public String toString() {
        return "IfElse(" + condition + ", " + thenStmtBlock + ", " + elseStmtBlock + ")";
    }
}