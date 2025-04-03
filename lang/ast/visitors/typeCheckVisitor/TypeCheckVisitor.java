package lang.ast.visitors.typeCheckVisitor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import lang.ast.LVisitor;
import lang.ast.Program;
import lang.ast.command.*;
import lang.ast.decl.*;
import lang.ast.expr.*;
import lang.ast.reserved.Data;
import lang.ast.types.*;

public class TypeCheckVisitor extends LVisitor {


    private LinkedList<String> errors;
    private Stack<VType> stk; // pilha de tipos)
    private Hashtable<String,TypeEntry> ctx; //tabela de tabelas de typeEntry(tipo da func e tabela de tipos de contexto local)
    private Hashtable<String, VType> localCtx;
    private Hashtable<Object, VType> typeNode;
    private VType returnType; // Tipo esperado de retorno
    private boolean bodyRetun; // Algum comando de retorno ?
    private ArrayList<String> logError;
        private final VTyError typeError = VTyError.newError();

    public TypeCheckVisitor(){
        errors = new LinkedList<String>();
        stk = new Stack<VType>();
        ctx = new Hashtable<String,TypeEntry>();
        typeNode = new Hashtable<Object,VType>();
        logError = new ArrayList<String>();
    }

    public Hashtable<String,TypeEntry> getTypeCtx(){
       return ctx;
    }

    public TypeEntry getFynctionType(String s){
       return ctx.get(s);
    }

    public VType typeOf(Object node){
       return typeNode.get(node);
    }

    public void visit(Program p){
        collectType(p.getFuncs());
         try{

             for(FunDef f : p.getFuncs()){
                 localCtx = ctx.get(f.getFname()).localCtx;
                 f.accept(this);
                }
            } catch (RuntimeException e) {
                logError.add(p.getLine() + ", " + p.getColumn() + ")" + e.getMessage());
            }
            
    }
    
    private void collectType(List<FunDef> lf) {
        for (FunDef f : lf) {
            TypeEntry e = new TypeEntry();
            e.sym = f.getFname();
            e.localCtx = new Hashtable<String, VType>();

            for (Param b : f.getParams()) {
                b.getType().accept(this);
                e.localCtx.put(b.getId(), stk.pop());
            }

            f.getRetrn();
            f.accept(this);
            VType returnType = stk.pop();
            e.ty = new VTyFunc(new VType[f.getRetrn().size() + 1]);

            ctx.put(f.getFname(), e);
            typeNode.put(f, e.ty);
        }
    }

    @Override
    public void visit(Data r){
        for(Decl f : r.getFields()){
            f.accept(this);
        }
        TypeEntry e = new TypeEntry();
        e.sym = r.getTypeName();
        e.localCtx = new Hashtable<String,VType>();
        for(Decl f : r.getFields()){
            f.accept(this);
            e.localCtx.put(f.getFieldName(),stk.peek());
        }
        ctx.put(r.getTypeName(),e);
    }


    //isso da certo pra um tipo de retorno, mas lang pode ter varios
    public void visit(FunDef d){

        d.getRetrn();
        d.accept(this);
        returnType = stk.pop();
        for(Param b: d.getParams()){
           b.accept(this);
        }
        bodyRetun = false;
        d.getBody().accept(this);
        if(!bodyRetun){
           throw new RuntimeException(
               "Erro de tipo (" + d.getLine() + ", " + d.getColumn() + ")  função " + d.getFname() + " não retorna valor algum");
        }
    }

    
    // @Override
    // public void visit(FunDefMultiReturn cmd) {
    //     // Aceitar os argumentos da função
    //     for (Exp ex : cmd.getArgs()) {
    //         ex.accept(this);
    //     }

    //     // Obter os tipos dos argumentos
    //     VType[] argTypes = new VType[cmd.getArgs().size()];
    //     for (int i = cmd.getArgs().size() - 1; i >= 0; i--) {
    //         argTypes[i] = stk.pop();
    //     }

    //     // Verificar se a função existe no contexto
    //     TypeEntry funcEntry = ctx.get(cmd.getID());
    //     if (funcEntry != null) {
    //         VTyFunc funcType = (VTyFunc) funcEntry.ty;

    //         // Verificar se os argumentos são compatíveis
    //         if (!funcType.matchArgs(argTypes)) {
    //             throw new RuntimeException("Erro de tipo (" + cmd.getLine() + ", " + cmd.getColumn() + ") chamada de função incompatível");
    //         }

    //         // Empilhar múltiplos retornos
    //         VType[] returnTypes = funcType.getReturnTypes();
    //         for (VType returnType : returnTypes) {
    //             stk.push(returnType);
    //         }

    //         // Associar os tipos retornados ao nó
    //         typeNode.put(cmd, returnTypes);
    //     } else {
    //         throw new RuntimeException("Erro de tipo (" + cmd.getLine() + ", " + cmd.getColumn() + ") chamada a função não declarada " + cmd.getID());
    //     }
    // }

    public void visit(Param d) {

        d.getType().accept(this);

        d.accept(this);
    }

    //não implementado
    // public void visit(CSeq d){

    //       d.getLeft().accept(this);

    //       d.getRight().accept(this);

    // }

    
    // public void visit(CAttr d){
    //       d.getExp().accept(this);
    //       if(localCtx.get(d.getVar().getClass()) == null){
    //           localCtx.put(d.getVar().getClass(),stk.pop());
    //       }else{
    //         VType ty = localCtx.get(d.getVar().getClass());
    //         if(!ty.match(stk.pop())){
    //            throw new RuntimeException(
    //            "Erro de tipo (" + d.getLine() + ", " + d.getColumn() + ") tipo da var " +d.getVar().getClass() + " incompativel"
    //            );
    //         }
    //       }
    // }

    public void visit(LoopCond d){

          d.getExp().accept(this);
          VType tyc = stk.pop();
          if (!(tyc.getTypeValue() == CLTypes.BOOL)) {
              throw new RuntimeException(
                      "Erro de tipo (" + d.getLine() + ", " +
                              d.getColumn() +
                              ") condição do laço deve ser bool");
          }
      
    }

    public void visit(AssignCmd cmd){
          cmd.getExp().accept(this);
          VType ty = stk.pop();
          if (localCtx.get(cmd.getVar().getClass()) == null) {
              localCtx.getOrDefault(cmd.getVar().getClass(),ty);
          }else{
            VType ty2 = localCtx.get(cmd.getVar().getClass());
            if (!ty2.match(ty)) {
                logError.add("Erro de tipo (" + cmd.getLine() + ", " + cmd.getColumn() + ") tipo da var " +cmd.getVar().getClass() + " incompativel");
               throw new RuntimeException(
               "Erro de tipo (" + cmd.getLine() + ", " + cmd.getColumn() + ") tipo da var " +cmd.getVar().getClass() + " incompativel"
               );
            }
          }
    }

    public void visit(If d){
          d.getCondition().accept(this);
          VType tyc = stk.pop();

          if(!(tyc.getTypeValue() == CLTypes.BOOL)){
              throw new RuntimeException(
               "Erro de tipo (" + d.getLine() + ", " +
                                  d.getColumn() +
                                  ") condição do teste deve ser bool"
               );
          }
          Hashtable<String,VType> lcal1 = (Hashtable<String,VType>)localCtx.clone();

          d.getThen().accept(this);


          if(d.getThen() != null){
             Hashtable<String,VType> lcal2 = (Hashtable<String,VType>)localCtx.clone();
             localCtx  = lcal1;
             d.getThen().accept(this);
             LinkedList<String> keys = new LinkedList<String>();
             for(java.util.Map.Entry<String,VType> ent : localCtx.entrySet()){
                if(! lcal2.containsKey(ent.getKey())){
                      keys.add(ent.getKey());
                      //System.out.println("To remove " + ent.getKey());
                }
             }
             for(String k : keys){localCtx.remove(k);}
          }else{
             localCtx = lcal1;
          }
    }


    public void visit(PrintCmd d){
         d.getExp().accept(this);
         VType td = stk.pop();
         if(td.getTypeValue() == CLTypes.INT ||
            td.getTypeValue() == CLTypes.FLOAT ||
            td.getTypeValue() == CLTypes.BOOL){
         }else{
             throw new RuntimeException("Erro de tipo (" + d.getLine() + ", " + d.getColumn() + ") Operandos incompatíveis");
         }

    }


    public void visit(BinOp e) {
            e.getLeft().accept(this);
            e.getRight().accept(this);
            VType td = stk.pop();
            VType te = stk.pop();
            if(td.getTypeValue() == CLTypes.INT &&
                te.getTypeValue() == CLTypes.INT){
                stk.push(VTyInt.newInt());
                typeNode.put(e,stk.peek());
            }else if(td.getTypeValue() == CLTypes.FLOAT &&
                    te.getTypeValue() == CLTypes.FLOAT){
                stk.push(VTyFloat.newFloat());
                typeNode.put(e,stk.peek());
            }else{
                throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") Operandos incompatíveis");
            }
    }

    public void visit(ModOperator e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        VType td = stk.pop();
        VType te = stk.pop();
        if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyInt.newInt());
            typeNode.put(e,stk.peek());
        }else{
            throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") Operandos incompatíveis");
        }
    }

    public void visit(MinusOperator  e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyInt.newInt());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyFloat.newFloat());
            typeNode.put(e,stk.peek());
         }else{
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") Operandos incompatíveis");
         }
    }

    public void visit(PlusOperator e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyInt.newInt());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyFloat.newFloat());
            typeNode.put(e,stk.peek());
         }else{
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") Operandos incompatíveis");
         }
    }

    public void visit(MultOperator e){
            e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyInt.newInt());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyFloat.newFloat());
            typeNode.put(e,stk.peek());
         }else{
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") Operandos incompatíveis");
         }
    }

    public void visit(DivOperator e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyInt.newInt());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyFloat.newFloat());
            typeNode.put(e,stk.peek());
         }else{
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") Operandos incompatíveis");
         }

    }

    public void visit(LtOperator e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else{
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") Operandos incompatíveis");
         }
    }

    public void visit(EqOperator e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.BOOL &&
                  te.getTypeValue() == CLTypes.BOOL){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else{
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") Operandos incompatíveis");
         }
    }


    public void visit(LValue e){
        VType ty = localCtx.get(e.getClass());
        if(ty == null){
           throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") variavel não declarada: " + e.getClass());
        }else{
          stk.push(ty);
          typeNode.put(e,stk.peek());
        }
    }

    public void visit(CallCmd e){

        for(Exp ex : e.getArgs()){
             ex.accept(this);
        }
        VType vt[] = new VType[e.getArgs().size()];
        for(int j = e.getArgs().size() -1; j >=0;j--){
            vt[j] = stk.pop();
        }
        TypeEntry tyd = ctx.get(e.getClass());
        if(tyd != null ){
           if(!((VTyFunc)tyd.ty).matchArgs(vt)){
               throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") chamada de função incompatível ");
           }
           stk.push(((VTyFunc)tyd.ty).getReturnType() );
           typeNode.put(e,stk.peek());
        }else{
           throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") chamada a função não declarada " + e.getClass());
        }
    }

    public void visit(IntLit e){   stk.push(VTyInt.newInt() ); typeNode.put(e,stk.peek());}
    public void visit(BoolLit e){  stk.push(VTyBool.newBool() ); typeNode.put(e,stk.peek());}
    public void visit(FloatLit e){ stk.push(VTyFloat.newFloat() ); typeNode.put(e,stk.peek());}
    public void visit(CharLit e){ stk.push(VTyChar.newChar() ); typeNode.put(e,stk.peek());}

    public void visit(TyBool t){  stk.push(VTyBool.newBool() ); typeNode.put(t,stk.peek());}
    public void visit(TyInt t){   stk.push(VTyInt.newInt() ); typeNode.put(t,stk.peek());}
    public void visit(TyFloat t){ stk.push(VTyFloat.newFloat()); typeNode.put(t,stk.peek());}
    public void visit(TyChar t){ stk.push(VTyChar.newChar()); typeNode.put(t,stk.peek());}

    @Override
    public void visit(IterateCmd cmd) {
        VType condType = checkCondition(cmd.getLoopCond());
        if (!(condType instanceof VTyBool)) {
            throw new RuntimeException("Loop condition must be of type bool");
        }
        cmd.getBody().accept(this);
    }

    private VType checkCondition(Exp cond) {
        return VTyBool.newBool();
    }


    public void visit(ReadCmd cmd) {
        VType varType = localCtx.get(cmd.getLValue().getClass());
        if (varType == null) {
            logError.add("Variable " + cmd.getLValue().getClass() + " not declared");
            throw new RuntimeException("Variable " + cmd.getLValue().getClass() + " not declared");
        }
    }

    public void visit(ReturnCmd cmd) {
        VType expType = checkExpression(cmd.getExps().get(0));
        if (!expType.match(returnType)) {
            throw new RuntimeException("Return type mismatch");
        }
        bodyRetun = true;
    }



     @Override
     public void visit(GtOperator e) {
         e.getLeft().accept(this);
         e.getRight().accept(this);
 
         VType tyr = stk.pop();
         VType tyl = stk.pop();
 
         if(tyr.match( tyr) && tyl.match(tyl)) {
             stk.push(VTyInt.newInt());
             typeNode.put(e,stk.peek());
         } else if(tyr.match(tyr) && tyl.match(tyl)) {
             stk.push(VTyFloat.newFloat());
             typeNode.put(e,stk.peek());
         } else {
             logError.add(
                     e.getLine() + ", " + e.getColumn() + ": Tipos " + tyl + " e " + tyr
                             + " não compatíveis com operador >(greater than)."
             );
 
             stk.push(typeError);
 
             throw new RuntimeException(e.getLine() + ", " + e.getColumn() + ": Tipos " + tyl + " e " + tyr
                     + " não compatíveis com operador >(greater than).");
         }
 
         if(tyr.getTypeValue() == CLTypes.INT &&
                 tyl.getTypeValue() == CLTypes.INT){
             stk.push(VTyBool.newBool());
             typeNode.put(e,stk.peek());
         }else if(tyr.getTypeValue() == CLTypes.FLOAT &&
                 tyl.getTypeValue() == CLTypes.FLOAT){
             stk.push(VTyBool.newBool());
             typeNode.put(e,stk.peek());
         }else{
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getColumn() + ") Operandos incompatíveis");
         }
     }


    private VType checkExpression(Exp exp) {
        // For simplicity, let's assume all expressions are int
        return VTyInt.newInt();
    }

    @Override
    public void visit(Decl p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(CallExp c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(New n) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Block p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(StmtBlock p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(IfElse p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(ArrayAccess p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(FieldAccess p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NeqOperator o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(AndOperator o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NotOperator o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(UnaryMinusOperator o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NullLit o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(TyId t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(TyNull t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }


}