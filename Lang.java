// Guilherme Ferreira 19.2.8981
//Breno Rotte 20.1.8124

import java.io.FileReader;
import java.io.IOException;

import java_cup.runtime.Symbol;
import lang.ast.LNode;
import lang.ast.visitors.Interp;
import lang.parser.LangLexer;
import lang.parser.LangParser;
import lang.parser.LangParserSym;
import lang.ast.visitors.typeCheckVisitor.*;

public class Lang {
    public static void runLexer(LangLexer lex) throws IOException, Exception {
        Symbol tk = lex.nextToken();

        while (tk.sym != LangParserSym.EOF) {
            System.out.println("(" + tk.left + "," + tk.right + ")" + LangParserSym.terminalNames[ tk.sym]);
            tk = lex.nextToken();
        }
        System.out.println(tk.toString());
    }

    public static void interpret(LangParser p) throws IOException, Exception {
        Symbol presult = p.parse();
        LNode root = (LNode) presult.value;
        if (root != null) {
            Interp v = new Interp();
            root.accept(v);
            System.out.println(v.getStackTop());
        } else {
            System.out.println("oops, erro!");
        }
    }

    public static void checkSyntax(LangParser p) throws IOException, Exception {
        Symbol presult;
        try {
            presult = p.parse();
            LNode root = (LNode) presult.value;
            if (presult != null) {
                System.out.println("accepted");
            } else {
                System.out.println("rejected");
            }
        } catch (Exception e) {
            System.out.println("rejected");
        }
    }
    
    public static void checkTypes(LangParser p) throws IOException, Exception {
        try{

            Symbol presult = p.parse();
            LNode root = (LNode) presult.value;
            if (root != null) {
                TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor();
                root.accept(typeCheckVisitor);
                System.out.println("well-typed");
            } else {
                System.out.println("ill-typed");
            }
        }catch(Exception e){
            System.out.println("ill-typed");

        }
    }

    public static void main(String args[]) throws IOException, Exception {
        int fname = 0;
        if (args.length < 1 || args.length > 2) {
            printHelp(); 
            System.exit(0);
        } else {
            if (args.length == 2) {
                fname = 1;
            }
            // Criando o Lexer
            LangLexer lex = new LangLexer(new FileReader(args[fname]));
            // Criando o parser (mais ainda não o executamos);
            LangParser p = new LangParser(lex);
            //Cria o sistema de tipos
            // TypeCheckVisitor typeCheckVisitormake = new TypeCheckVisitor();
            // Testando os argumentos para determinar o que fazer em seguida.
            if (args.length == 2 && args[0].equals("-lex")) {
                runLexer(lex);
                System.out.println("end token");
                System.exit(0);
            } else if (args.length == 2 && args[0].equals("-i")) {
                interpret(p);
                System.exit(0);
            } else if (args.length == 2 && args[0].equals("-syn")) {
                checkSyntax(p);
                System.exit(0);
            } else if (args.length == 2 && args[0].equals("-ty")) {
                checkTypes(p);
                System.exit(0);
            } else {
                printHelp();
            }
        }
    }

    private static void printHelp() { 
        System.out.println("use java Lang [opcao] <nome-de-arquivo>");
        System.out.println("opcao: ");
        System.out.println("   -lex  : Apenas lista os tokens.");
        System.out.println("   -i    : Interpreta o programa.");
        System.out.println("   -syn  : Apenas verifica a sintaxe.");
        System.out.println("   -ty   : Apenas verifica os tipos e retorna 'well typed' ou a lista de erros.");
    }
}