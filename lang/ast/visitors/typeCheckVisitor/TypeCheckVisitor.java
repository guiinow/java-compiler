package typeCheckVisitor;

import lang.ast.command.*;
import lang.ast.decl.FunDef;
import lang.ast.expr.*;
import lang.ast.types.TyBool;
import lang.ast.types.TyFloat;
import lang.ast.types.TyInt;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

import lang.ast.LVisitor;
import lang.ast.Program;

// Int
// Char
// Bool
// Float
// true
// false
// if
// else
// data
// iterate
// read
// print
// return
// null
// gt > 
// lt <

public class TypeCheckVisitor extends LVisitor {


    private LinkedList<String> errors;
    private Stack<VType> stk;
    private Hashtable<String,TypeEntry> ctx;
    private Hashtable<String, VType> localCtx;
    private Hashtable<Object, VType> typeNode;
    private VType returnType; // Tipo esperado de retorno
    private boolean bodyRetun; // Algum comando de retorno ?

    public TyChecker(){
        errors = new LinkedList<String>();
        stk = new Stack<VType>();
        ctx = new Hashtable<String,TypeEntry>();
        typeNode = new Hashtable<Object,VType>();
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
         for(FunDef f : p.getFuncs()){
             localCtx = ctx.get(f.getFname()).localCtx;
             f.accept(this);
         }
    }
    
private void collectType(ArrayList<FunDef> lf){
        for(FunDef f : lf){

              TypeEntry e = new TypeEntry();
              e.sym = f.getFname();
              e.localCtx = new Hashtable<String,VType>();

              int typeln = f.getParams().size() + 1;
              for(Bind b : f.getParams()){
                 b.getType().accept(this);
                 e.localCtx.put( b.getVar().getName(), stk.peek());
              }
              f.getRet().accept(this);
              VType[] v = new VType[typeln];
              for(int i = typeln - 1; i >=0; i--){
                  v[i] = stk.pop();
              }

              e.ty =  new VTyFunc(v);

              ctx.put(f.getFname(),e);
              typeNode.put(f,e.ty);
         }
    }

    public void visit(FunDef d){

        d.getRet().accept(this);
        returnType = stk.pop();
        for(Bind b: d.getParams()){
           b.accept(this);
        }
        bodyRetun = false;
        d.getBody().accept(this);
        if(!bodyRetun){
           throw new RuntimeException(
               "Erro de tipo (" + d.getLine() + ", " + d.getCol() + ")  função " + d.getFname() + " não retorna valor algum");
        }
    }

    public void visit(Bind  d){

        d.getType().accept(this);

        d.getVar().accept(this);
    }

    public void visit(CSeq d){

          d.getLeft().accept(this);

          d.getRight().accept(this);

    }

    public void visit(CAttr d){
          d.getExp().accept(this);
          if(localCtx.get(d.getVar().getName()) == null){
              localCtx.put(d.getVar().getName(),stk.pop());
          }else{
            VType ty = localCtx.get(d.getVar().getName());
            if(!ty.match(stk.pop())){
               throw new RuntimeException(
               "Erro de tipo (" + d.getLine() + ", " + d.getCol() + ") tipo da var " +d.getVar().getName() + " incompativel"
               );
            }
          }
    }

    public void visit(Loop d){

          d.getCond().accept(this);
          VType tyc = stk.pop();
          if(! (tyc.getTypeValue() == CLTypes.BOOL)){
              throw new RuntimeException(
               "Erro de tipo (" + d.getLine() + ", " +
                                  d.getCol() +
                                  ") condição do laço deve ser bool"
               );
          }
          d.getBody().accept(this);
    }

    public void visit(If d){
          d.getCond().accept(this);
          VType tyc = stk.pop();

          if(!(tyc.getTypeValue() == CLTypes.BOOL)){
              throw new RuntimeException(
               "Erro de tipo (" + d.getLine() + ", " +
                                  d.getCol() +
                                  ") condição do teste deve ser bool"
               );
          }
          Hashtable<String,VType> lcal1 = (Hashtable<String,VType>)localCtx.clone();

          d.getThn().accept(this);


          if(d.getEls() != null){
             Hashtable<String,VType> lcal2 = (Hashtable<String,VType>)localCtx.clone();
             localCtx  = lcal1;
             d.getEls().accept(this);
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

    public void visit(Return d){
         d.getExp().accept(this);
         if(!stk.peek().match(returnType)){
            throw new RuntimeException("Erro de tipo (" + d.getLine() + ", " + d.getCol() + ") Tipo de retorno incompatível.");
         }
         typeNode.put(d,stk.pop());
         bodyRetun = true;
    }

    public void visit(Print d){
         d.getExp().accept(this);
         VType td = stk.pop();
         if(td.getTypeValue() == CLTypes.INT ||
            td.getTypeValue() == CLTypes.FLOAT ||
            td.getTypeValue() == CLTypes.BOOL){
         }else{
             throw new RuntimeException("Erro de tipo (" + d.getLine() + ", " + d.getCol() + ") Operandos incompatíveis");
         }

    }

    public void visit(BinOp e){ }

    public void visit(Sub  e){
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
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }

    public void visit(Plus e){
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
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }

    public void visit(Times e){
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
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }

    public void visit(Div e){
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
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }

    }

    public void visit(Lte e){
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
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }


    public void visit(Lt e){
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
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }

    public void visit(Eq e){
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
             throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }


    public void visit(Var e){
        VType ty = localCtx.get(e.getName());
        if(ty == null){
           throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") variavel não declarada: " + e.getName());
        }else{
          stk.push(ty);
          typeNode.put(e,stk.peek());
        }
    }

    public void visit(FCall e){

        for(Exp ex : e.getArgs()){
             ex.accept(this);
        }
        VType vt[] = new VType[e.getArgs().size()];
        for(int j = e.getArgs().size() -1; j >=0;j--){
            vt[j] = stk.pop();
        }
        TypeEntry tyd = ctx.get(e.getID() );
        if(tyd != null ){
           if(!((VTyFunc)tyd.ty).matchArgs(vt)){
               throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") chamada de função incompatível ");
           }
           stk.push(((VTyFunc)tyd.ty).getReturnType() );
           typeNode.put(e,stk.peek());
        }else{
           throw new RuntimeException("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") chamada a função não declarada " + e.getID());
        }
    }

    public void visit(IntLit e){   stk.push(VTyInt.newInt() ); typeNode.put(e,stk.peek());}
    public void visit(BoolLit e){  stk.push(VTyBool.newBool() ); typeNode.put(e,stk.peek());}
    public void visit(FloatLit e){ stk.push(VTyFloat.newFloat() ); typeNode.put(e,stk.peek());}

    public void visit(TyBool t){  stk.push(VTyBool.newBool() ); typeNode.put(t,stk.peek());}
    public void visit(TyInt t){   stk.push(VTyInt.newInt() ); typeNode.put(t,stk.peek());}
    public void visit(TyFloat t){ stk.push(VTyFloat.newFloat()); typeNode.put(t,stk.peek());}


    @Override
    public void visit(IterateCmd cmd) {
        VType condType = checkCondition(cmd.getLoopCond());
        if (!(condType instanceof VTyBool)) {
            throw new TypeError("Loop condition must be of type bool");
        }
        // Visit the body of the loop
        cmd.getBody().accept(this);
    }

    private VType checkCondition(Exp cond) {
        // Implement your condition type checking logic here
        // For simplicity, let's assume all conditions are boolean
        return VTyBool.newBool();
    }

    @Override
    public void visit(If cmd) {
        VType condType = checkCondition(cmd.getCondition());
        if (!(condType instanceof VTyBool)) {
            throw new TypeError("If condition must be of type bool");
        }
        cmd.getThenBlock().accept(this);
        if (cmd.getElseBlock() != null) {
            cmd.getElseBlock().accept(this);
        }
    }

    @Override
    public void visit(PrintCmd cmd) {
        VType expType = checkExpression(cmd.getExp());
        // Assuming print can handle any type
    }

    private VType checkExpression(Exp exp) {
        // Implement your expression type checking logic here
        // For simplicity, let's assume all expressions are int
        return VTyInt.newInt();
    }
}