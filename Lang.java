
import java.io.*;
import java_cup.runtime.*;
import lang.ast.Node;
import lang.parser.LangParser;
import lang.parser.LangParserSym;
import lang.parser.LangLexer;
import lang.ast.visitors.*;

public class Lang{

     public static void runLexer(LangLexer lex)  throws IOException,Exception{
           Symbol tk = lex.nextToken();
           while(tk.sym != LangParserSym.EOF){
               System.out.println("(" + tk.left + ","  + tk.right + ")" +
                                  LangParserSym.terminalNames[tk.sym]  +
                                  (tk.value == null ?  "" : (" val=" + tk.value.toString()) ) );
               tk = lex.nextToken();
           }
           System.out.println(LangParserSym.terminalNames[tk.sym]);
     }


    public static void interpret(LangParser p)  throws IOException,Exception{
        Node root;
        try{
           Symbol presult = p.parse();
           root = (Node)presult.value;
        }catch(Exception e){ root = null; }
        if(root != null){
              Interp interp = new Interp();
              root.accept(interp);
              // Para propósitos de testes apenas
              // Ao entregar o trabalho, comemte as linhas de impressão a seguir:
              interp.printMemory();
              if(interp.emptyStack()){
                 System.out.println("topo da pilha: vazia");
              }else{
                 System.out.println("topo da pilha: " +  interp.getStkTop().toString());
              }
        }else{
              System.out.println("Parser error");
        }
    }

    public static void checkSyntax(LangParser p)  throws IOException,Exception{
        Node root;
        try{
           Symbol presult = p.parse();
           root = (Node)presult.value;
        }catch(Exception e){
           root = null;
        }
        if(root != null){
              System.out.println("accepted");
        }else{
              System.out.println("rejected");
        }
    }

     public static void main(String args[])  throws IOException,Exception {
          int fname = 0;
          if(args.length < 1 || args.length > 2){
             printHelp();
             System.exit(0);
          }else{
              if(args.length == 2){ fname = 1;}
              // Criando o Lexer
              LangLexer lex = new LangLexer(new FileReader(args[fname]));
              // Criando o parser (mais ainda não o executamos);
              LangParser p = new LangParser(lex);
              // Testando os argumentos para determinar o que fazer em seguida.
              if(args.length == 2 && args[0].equals("-lex")){
                    runLexer(lex);
                    System.exit(0);
              }else if(args.length == 2 && args[0].equals("-i")){
                    interpret(p);
                    System.exit(0);
              }else if(args.length == 2 && args[0].equals("-syn")){
                    checkSyntax(p);
                    System.exit(0);
              }
          }
     }

     private static void printHelp(){
         System.out.println("use java Lang [opcao] <nome-de-arquivo>");
         System.out.println("opcao: ");
         System.out.println("   -lex  : Apenas lista os tokens.");
         System.out.println("   -i    : Interpreta o programa.");
         System.out.println("   -syn  : Apenas verifica a sintaxe.");
     }
}
