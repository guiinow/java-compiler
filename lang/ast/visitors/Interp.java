package lang.ast.visitors;


//import lang.ast.decl.*;
import java.awt.Event;
import java.util.Stack;
import lang.ast.command.*;
import lang.ast.expr.*;
import lang.ast.types.*;


public class Interp {
    
    public Stack<Object> stk;

    public Interp(){
        stk = new Stack<Object>();
    }

    public Integer getStackTop() {
        return stk.peek();
    }

    public void visit(Attrib c) {
        System.out.println("Atribuindo " + c.getVar().getName() + " = " + c.getExpr().accept(this));
        Object value = stk.pop();
        System.out.println("Valor atribuído: " + value);
        c.getVar().setValue(value);
    }

    public void visit(Sub  e){}

    public void visit(Plus e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer r = (Integer)stk.pop() + (Integer)stk.pop();
        stk.push(r);
    }

    public void visit(Times e) {
         e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer r = (Integer)stk.pop() * (Integer)stk.pop();
        stk.push(r);
    }
    public void visit(Div e) {}

    public void visit(Var e) {
        stk.push(e.getValue());
    }

    public void visit(IntLit e) {
        stk.push(e.getValue());
    }

    public void visit(BoolLit e) {
        stk.push(e.getValue());
    }

    public void visit(TyBool t) {}
    public void visit(TyInt t) {}

}
