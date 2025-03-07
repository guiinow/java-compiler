package lang.ast.command;

import lang.ast.Node;
import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;

public class Attrib extends Node {

      private Exp lhs;
      private Exp e;
      public Attrib(int line, int col, Exp lhs, Exp e){
           super(line,col);
           this.lhs = lhs;
           this.e = e;
      }

      public Exp getExp(){return e;}
      public Exp getLhs(){return lhs;}

      public void accept(NodeVisitor v){v.visit(this);}


}

