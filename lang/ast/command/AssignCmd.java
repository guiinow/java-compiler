package lang.ast.command;

import lang.ast.LVisitor;
import lang.ast.expr.Exp;
import lang.ast.expr.Var;

public class AssignCmd extends Cmd {
   private Var var; // Lado esquerdo da atribuição (variável)
   private Exp exp; // Lado direito da atribuição (expressão)

   public AssignCmd(int line, int column, Var var, Exp exp) {
      super(line, column);
      this.var = var;
      this.exp = exp;
   }

   public Var getVar() {
      return var;
   }

   public Exp getExp() {
      return exp;
   }

   @Override
   public void accept(LVisitor visitor) {
      visitor.visit(this);
   }
}