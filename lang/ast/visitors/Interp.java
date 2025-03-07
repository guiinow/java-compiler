package lang.ast.visitors;

import lang.ast.LVisitor;
import lang.ast.Program;
import lang.ast.expr.*;
import lang.ast.reserved.*;

import java.util.*;
import lang.ast.types.*;
import lang.ast.command.*;
import lang.ast.decl.*;
import lang.ast.delimiters.*;
import lang.ast.eof.*;

public class Interp extends LVisitor {

  public Stack<Object> stk;
  private Map<String, Object> varTable; // Tabela de variáveis

  public Interp() {
    stk = new Stack<Object>();
    varTable = new HashMap<String, Object>(); // Inicializa a tabela de variáveis
  }

  public Object getStackTop() {
    return stk.peek();
  }

  // Método para obter o valor de uma variável
  public Object getVarValue(String id) {
    return varTable.get(id);
  }

  // Método para definir o valor de uma variável
  public void setVarValue(String id, Object value) {
    varTable.put(id, value);
  }

  public void visit(Program p) {
    for (Data data : p.getData()) {
      data.accept(this);
    }

    for (FunDef func : p.getFuncs()) {
      func.accept(this);
    }
  }

  public void visit(PlusOperator e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);
    Number right = (Number) stk.pop();
    Number left = (Number) stk.pop();

    if (left instanceof Float || right instanceof Float) {
      float r = left.floatValue() + right.floatValue();
      stk.push(r);
    } else {
      int r = left.intValue() + right.intValue();
      stk.push(r);
    }
  };

  public void visit(MultOperator e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);

    Number right = (Number) stk.pop();
    Number left = (Number) stk.pop();

    if (left instanceof Float || right instanceof Float) {
      float r = left.floatValue() * right.floatValue();
      stk.push(r);
    } else {
      int r = left.intValue() * right.intValue();
      stk.push(r);
    }
  };

  public void visit(MinusOperator e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);

    Number right = (Number) stk.pop();
    Number left = (Number) stk.pop();

    if (left instanceof Float || right instanceof Float) {
      float r = left.floatValue() - right.floatValue();
      stk.push(r);
    } else {
      int r = left.intValue() - right.intValue();
      stk.push(r);
    }
  }

  public void visit(UnaryMinusOperator e) {
    e.getExp().accept(this);

    Object value = stk.pop();

    if (value instanceof Integer) {
      stk.push(-(Integer) value);
    } else if (value instanceof Float) {
      stk.push(-(Float) value);
    } else {
      throw new RuntimeException("Unary '-' applied to non-number: " + value);
    }
  }

  public void visit(DivOperator e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);

    float right = ((Number) stk.pop()).floatValue(); // divisor
    float left = ((Number) stk.pop()).floatValue(); // dividendo

    float r = left / right;
    stk.push(r);
  }

  public void visit(ModOperator e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);

    float right = ((Number) stk.pop()).floatValue();
    float left = ((Number) stk.pop()).floatValue();

    float r = left % right;
    stk.push(r);
  }

  public void visit(GtOperator e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);

    float right = ((Number) stk.pop()).floatValue();
    float left = ((Number) stk.pop()).floatValue();

    boolean r = left > right;
    stk.push(r);
  }

  public void visit(LtOperator e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);

    float right = ((Number) stk.pop()).floatValue();
    float left = ((Number) stk.pop()).floatValue();

    boolean r = left < right;
    stk.push(r);
  }

  public void visit(EqOperator e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);

    boolean r = ((Number) stk.pop()).floatValue() == ((Number) stk.pop()).floatValue();
    stk.push(r);
  }

  public void visit(NeqOperator e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);

    boolean r = ((Number) stk.pop()).floatValue() != ((Number) stk.pop()).floatValue();
    stk.push(r);
  }

  public void visit(AndOperator e) {
    e.getLeft().accept(this);
    boolean left = (Boolean) stk.pop();

    e.getRight().accept(this);
    boolean right = (Boolean) stk.pop();

    boolean r = left && right;
    stk.push(r);
  }

  public void visit(NotOperator e) {
    e.getRight().accept(this);
    boolean right = (Boolean) stk.pop();

    boolean r = !right;
    stk.push(r);
  }

  public void visit(LParen d) {
  }

  public void visit(RParen d) {
  }

  public void visit(LBracket d) {
  }

  public void visit(RBracket d) {
  }

  public void visit(Attrib e) {
    // e.getRight().accept(this);
    // Var left = e.getLeft();

    // Object right = stk.pop();
    // left.setValue(right);

    // stk.push(right);
  }

  public void visit(AssignCmd c) {
    // Avalia a expressão do lado direito
    c.getExp().accept(this);

    // Obtém o valor da expressão
    Object value = stk.pop();

    // Armazena o valor na variável do lado esquerdo
    setVarValue(c.getVar().getName(), value);

    // Empilha o valor novamente (opcional, dependendo da semântica da linguagem)
    stk.push(value);
  }

  public void visit(Var e) {
    // Obtém o valor da variável da tabela
    Object value = getVarValue(e.getName());

    // Empilha o valor na pilha
    stk.push(value);
  }

  public void visit(IntLit e) {
    stk.push(e.getValue());
  };

  public void visit(BoolLit e) {
    stk.push(e.getValue());
  };

  public void visit(NullLit e) {
    stk.push(e.getValue());
  };

  public void visit(TyBool t) {
    System.out.println("Tipo: " + t.getTypeName());
    stk.push(t.getTypeName());
  };

  public void visit(TyInt t) {
    System.out.println("Tipo: " + t.getTypeName());
    stk.push(t.getTypeName());
  }

  public void visit(Type t) {
    System.out.println("Tipo: " + t.getTypeName());
    stk.push(t.getTypeName());
  }

  public void visit(TyId t) {
    System.out.println("Tipo: " + t.getTypeName());
    stk.push(t.getTypeName());
  }

  public void visit(TyFloat t) {
    System.out.println("Tipo: " + t.getTypeName());
    stk.push(t.getTypeName());
  }

  public void visit(TyChar t) {
    System.out.println("Tipo: " + t.getTypeName());
    stk.push(t.getTypeName());
  }

  public void visit(TyNull t) {
    System.out.println("Tipo: " + t.getTypeName());
    stk.push(t.getTypeName());
  }

  public void visit(FunDef p) {
    stk.push(p.getParams());
    stk.push(p.getBody());
    stk.push(p.getRetrn());
  }

  public void visit(Bind p) {
    stk.push(p.getVar());
    stk.push(p.getType());
  }

  public void visit(Decl p) {
    stk.push(p.getFieldName());
    stk.push(p.getFieldType());
  }

  public void visit(Block p) {
    for (Cmd cmd : p.getCommands()) {
      cmd.accept(this);
    }
    stk.push(p.getCommands());
  }

  public void visit(DotOperator o) {
  }

  public void visit(Cmd o) {

  }

  public void visit(StmtBlock o) {

  }

  public void visit(ReadCmd p) {

  }

  public void visit(PrintCmd p) {

  }

  public void visit(ReturnCmd p) {

  }

  public void visit(ArrayAccess p) {

  }

  public void visit(FieldAccess p) {

  }

  public void visit(LValue p) {

  }

  public void visit(FloatLit o) {
    stk.push(o.getValue());
  }

  public void visit(CharLit o) {
    stk.push(o.getValue());
  }

  public void visit(Comma d) {
  }

  public void visit(LBrace d) {
  }

  public void visit(RBrace d) {
  }

  public void visit(Semicolon d) {
  }

  public void visit(Data r) {
    stk.push(r.getFields());
    stk.push(r.getTypeName());
  }

  public void visit(Param d) {
  }

  public void visit(If r) {
  }

  public void visit(IfElse r) {

  }

  public void visit(Iterate r) {
  }

  public void visit(Print r) {
  }

  public void visit(Read r) {
  }

  public void visit(Res r) {
  }

  public void visit(Return r) {
  }

  public void visit(EOF e) {
  };

}