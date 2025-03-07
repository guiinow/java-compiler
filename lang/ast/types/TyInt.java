package lang.ast.types;


import lang.ast.NodeVisitor;



public class TyInt extends LType {

      public TyInt(int line, int col){
          super(line,col);
      }
      public void accept(NodeVisitor v){v.visit(this);}

}
