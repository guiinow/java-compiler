package lang.ast.command;

import lang.ast.expr.LoopCond;
import lang.ast.LVisitor;
import lang.ast.decl.StmtBlock;

public class IterateCmd extends Cmd {
    private LoopCond loopCond;
    private StmtBlock body;

    public IterateCmd(int line, int column, LoopCond loopCond, StmtBlock body) {
        super(line, column);
        this.loopCondString = loopCondString;
        this.loopCondExpressao = loopCondExpressao;
        this.body = body;
    }

    public String getLoopCondString()
    {
        return loopCondString;
    }
    
     public Exp getLoopCondExpressao() {
        return loopCondExpressao;
    }

    public StmtBlock getBody() {
        return body;
    }

    @Override
    public void accept(LVisitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString() {
        return "IterateCmd(" + loopCondString + ", " + body + ")"; //colocar pra imprimir o loopcond expressao
    }
}