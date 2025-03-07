package lang.ast.expr;
import lang.ast.NodeVisitor;

public class BoolLit extends Exp{

      private boolean value;
      public BoolLit(int line, int col, boolean value){
           super(line,col);
           this.value = value;
      }

      public boolean getValue(){ return value;}


      public void accept(NodeVisitor v){v.visit(this);}


}
