package lang.ast.command;

import lang.ast.Node;
import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;

public class Print extends Node {
      private Exp e;
      public Print(int line, int col, Exp e){
           super(line,col);
           this.e = e;
      }

      public Exp getExp(){return e;}

      public void accept(NodeVisitor v){v.visit(this);}
}

