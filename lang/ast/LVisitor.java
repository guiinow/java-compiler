package lang.ast;

import lang.ast.command.*;
import lang.ast.decl.*;
import lang.ast.eof.*;
import lang.ast.expr.*;
import lang.ast.reserved.*;
import lang.ast.types.*;

public abstract class LVisitor {
    public abstract void visit(LoopCond p);

    public abstract void visit(FunDef p);

    public abstract void visit(Decl p);

    public abstract void visit(CallCmd c);
    public abstract void visit(CallExp c);

    public abstract void visit(New n);

    public abstract void visit(Block p);

    public abstract void visit(StmtBlock p);

    public abstract void visit(Cmd p);

    public abstract void visit(Param p);

    public abstract void visit(If p);

    public abstract void visit(IfElse p);

    public abstract void visit(ReadCmd p);

    public abstract void visit(PrintCmd p);

    public abstract void visit(ReturnCmd p);

    public abstract void visit(ArrayAccess p);

    public abstract void visit(FieldAccess p);

    public abstract void visit(LValue p);

    // command

    public abstract void visit(AssignCmd c);

    // expr
    public abstract void visit(PlusOperator o); // PLUS

    public abstract void visit(MinusOperator o); // MINUS

    public abstract void visit(MultOperator o); // MULT

    public abstract void visit(DivOperator o); // DIV

    public abstract void visit(ModOperator o); // MOD

    public abstract void visit(EqOperator o); // EQ

    public abstract void visit(NeqOperator o); // NEQ

    public abstract void visit(LtOperator o); // LT

    public abstract void visit(GtOperator o); // GT

    public abstract void visit(AndOperator o); // AND

    public abstract void visit(NotOperator o); // NOT


    public abstract void visit(UnaryMinusOperator o); // DOT

    public abstract void visit(BoolLit o);

    public abstract void visit(NullLit o);

    public abstract void visit(IntLit o);

    public abstract void visit(FloatLit o);

    public abstract void visit(CharLit o);



    // types
    public abstract void visit(TyId t);

    public abstract void visit(TyBool t);

    public abstract void visit(TyInt t);

    public abstract void visit(TyFloat t);

    public abstract void visit(TyChar t);

    public abstract void visit(TyNull t);

    public abstract void visit(Type t);

    // delimiters




    // reserved
    public abstract void visit(Data r);

    public abstract void visit(IterateCmd r);

    public abstract void visit(Program p);




    // EOF
    public abstract void visit(EOF e);
}