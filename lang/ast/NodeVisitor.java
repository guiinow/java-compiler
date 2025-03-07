package lang.ast;

import lang.ast.decl.*;
import lang.ast.expr.*;
import lang.ast.command.*;
import lang.ast.types.*;


public abstract class NodeVisitor{

    public abstract void visit(LProg p);

    public abstract void visit(Attrib c);
    public abstract void visit(Seq c);
    public abstract void visit(Print c);

    public abstract void visit(TyBind d);

    public abstract void visit(Sub  e);
    public abstract void visit(Plus e);
    public abstract void visit(Times e);
    public abstract void visit(Div e);
    public abstract void visit(Mod e);
    public abstract void visit(Var e);
    public abstract void visit(IntLit e);
    public abstract void visit(BoolLit e);

    public abstract void visit(TyBool t);
    public abstract void visit(TyInt t);

}
