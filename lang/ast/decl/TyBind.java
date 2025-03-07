package lang.ast.decl;

import lang.ast.NodeVisitor;
import lang.ast.Node;
import lang.ast.expr.Var;
import lang.ast.types.LType;
public class TyBind extends Node{

      private Var var;
      private LType t;

      public TyBind(int line, int col, Var v, LType t){
           super(line,col);
           this.var = v;
           this.t = t;
      }

      public Var getVar(){ return var;}
      public LType getType(){ return t;}

      public void accept(NodeVisitor v){v.visit(this);}

}
