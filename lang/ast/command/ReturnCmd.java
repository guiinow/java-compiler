package lang.ast.command;

import java.util.ArrayList;
import java.util.List;
import lang.ast.LVisitor;
import lang.ast.expr.Exp;

public class ReturnCmd extends Cmd {
    // Lista de expressões que serão retornadas
    private List<Exp> exps;

    /**
     * Construtor para ReturnCmd.
     * @param line  número da linha onde o comando ocorre
     * @param column  coluna onde o comando ocorre
     * @param exps lista de expressões adicionais (pode ser vazia ou nula)
     */
    public ReturnCmd(int line, int column, List<Exp> exps) {
        super(line, column);
        this.exps = new ArrayList<>();
        // Se houver expressões adicionais, as adiciona à lista
        if (exps != null) {
            this.exps.addAll(exps);
        }
    }

    public List<Exp> getExps() {
        return exps;
    }

    @Override
    public void accept(LVisitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString() {
        return "ReturnCmd(" + exps + ")";
    }
}
