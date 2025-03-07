package lang.ast;

import java.util.ArrayList;
import lang.ast.decl.*;
import lang.ast.types.*;
import lang.ast.command.*;

public class LProg extends Node{

   public LProg(int l, int c){
        super(l,c);
   }

   //
   // Modifique essa classe para que ela represente um programa
   // Sugestão: Um programa deve conter uma lista de definições,
   //           das algumas serão definições de Registros (data)
   //           e algumas serão funções. Você pode usar dois ArrayList
   //           uma para definições de tipos de dados e outro para
   //           definições de funções.

   public void accept(NodeVisitor v){v.visit(this);}

}
