package lang.ast.command;

import java.util.ArrayList;
import java.util.List;
import lang.ast.expr.Exp;
import lang.ast.LVisitor;

public class ReturnCmd extends Cmd {
    // Lista de expressões que serão retornadas
    private List<Exp> exps;

    /**
     * Construtor para ReturnCmd.
     * @param line  número da linha onde o comando ocorre
     * @param column  coluna onde o comando ocorre
     * @param e a expressão obrigatória (primeiro valor de retorno)
     * @param exps lista de expressões adicionais (pode ser vazia ou nula)
     */
    public ReturnCmd(int line, int column, Exp e, List<Exp> exps) {
        super(line, column);
        this.exps = new ArrayList<>();
        // Adiciona a expressão obrigatória
        this.exps.add(e);
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
