package lang.ast.command;

import lang.ast.Node;
import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;

public class Seq extends Node {

      private Node head;
      private Node tail;

      public Seq(int line, int col, Node h, Node t){
           super(line,col);
           head = h;
           tail = t;
      }

      public Node getHead(){return head;}
      public Node getTail(){return tail;}

      public void accept(NodeVisitor v){v.visit(this);}
}

