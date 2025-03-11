package lang.ast.command;

import lang.ast.LVisitor;
import lang.ast.expr.Exp;

public class IterateCmd extends Cmd {
    private String loopCondString;
    private Exp loopCondExpressao;
    private Cmd body;

    public IterateCmd(int line, int column, String loopCondString, Exp loopCondExpessao, Cmd body) {
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

    public Cmd getBody() {
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