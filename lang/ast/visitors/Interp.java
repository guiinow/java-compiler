package lang.ast.visitors;

import lang.ast.NodeVisitor;

import lang.ast.*;
import lang.ast.decl.*;
import lang.ast.expr.*;
import lang.ast.command.*;
import lang.ast.types.*;

import java.util.*;

public  class Interp extends NodeVisitor{

    // Uma pilha é o bastante para fazer
    // computações simples.
    private Stack<Object> stk;

    // Vamos usar uma tabela hash para implementar a memória (Environment) do programa.
    // Equanto a pilha vai guardar resultados de computações intermediárias
    // A memória (Environment) irá guardar os valores das variáveis.
    private HashMap<String,Object> env;

    // Construtor padrão. Apeas inicializa a pilha e o Environment !
    public Interp(){
       env = new HashMap<String,Object>();
       stk = new Stack<Object>();
    }

    public Object getStkTop(){
       return stk.peek();
    }

    public boolean emptyStack(){
       return stk.isEmpty();
    }

    public void printMemory(){
        System.out.println("------------ MEMÓRIA ---------------");
        for(Map.Entry<String,Object> e : env.entrySet()){
            System.out.println(e.getKey() + " : " + e.getValue().toString());
        }
        System.out.println("----------FIM DE MEMÓRIA ---------------");
    }

    public void visit(LProg p){}

    public void visit(Attrib c){
        String vname = ((Var)c.getLhs()).getName();
        c.getExp().accept(this);
        env.put(vname,stk.pop());
    }

    public void visit(Seq c){
        c.getHead().accept(this);
        c.getTail().accept(this);
    }

    public void visit(Print c){
        c.getExp().accept(this);
        System.out.print(stk.pop());
    }

    public void visit(TyBind d){}

    public void visit(Sub  e){}

    public void visit(Plus e){
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer r = (Integer)stk.pop();
        Integer l = (Integer)stk.pop();
        stk.push(l + r);
    }

    public void visit(Times e){
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer r = (Integer)stk.pop();
        Integer l = (Integer)stk.pop();
        stk.push(l * r);
    }

    public void visit(Div e){}

    public void visit(Var e){
       Object o = env.get(e.getName());
       if(o != null){
          stk.push(o);
       }else{
          throw new RuntimeException("Variavel " + e.getName() + " não foi inicializada");
       }
    }


    public void visit(Mod e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer r = (Integer)stk.pop();
        Integer l = (Integer)stk.pop();
        stk.push(l % r);
    }
    public void visit(IntLit e){ stk.push(e.getValue());}
    public void visit(BoolLit e){stk.push(e.getValue());}


    // Se uma construção em particular não for necessária (não vamos usar) por
    // alguma razão NÃO APAGUE ! deixe o corpo da função vazio !
    public void visit(TyBool t){}
    public void visit(TyInt t){}

}
