package lang.ast;

//import lang.ast.decl.*;
import lang.ast.expr.*;
import lang.ast.command.*;
import lang.ast.types.*;


public abstract class NodeVisitor{

    public abstract void visit(Attrib c);

    public abstract void visit(Sub  e);
    public abstract void visit(Plus e);
    public abstract void visit(Times e);
    public abstract void visit(Div e);
    public abstract void visit(Mod e);
    public abstract void visit(Equal e);
    public abstract void visit(Different e);
    public abstract void visit(Less e);
    public abstract void visit(Less_equal e);
    public abstract void visit(Greater e);
    public abstract void visit(Greater_equal e);
    public abstract void visit(Var e);
    public abstract void visit(Int e);
    public abstract void visit(And e);
    public abstract void visit(Not e);
    public abstract void visit(lang.ast.expr.Float e);
    public abstract void visit(Bool e);

    public abstract void visit(TyBool t);
    public abstract void visit(TyInt t);

}
