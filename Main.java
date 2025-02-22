// Autores:
// Guilherme Ferreira
// Breno Rotte

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java_cup.runtime.Symbol;
import parser.LangParser;
import parser.LangParserSym;
import parser.LangLexer;

public class Main {

    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public static void main(String[] args) {
        if (args.length != 1) {
            help();
            System.exit(0);
        }

        try {
            // Inicializa o analisador léxico
            LangLexer lexer = new LangLexer(new FileReader(args[0]));
            LangParser p = new LangParser(lexer);
            p.parse();

        } catch (FileNotFoundException e) {
            System.out.println("O arquivo " + args[0] + " não foi encontrado.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo " + args[0] + ".");
        } catch (Exception e) {
            System.out.println("Erro ao processar o arquivo " + args[0] + ".");
            e.printStackTrace();
        }
    }

    public static void help() {
        System.out.println("Analisador léxico e sintático para a linguagem Lang.");
        System.out.println("Versão: 1.0");
        System.out.println("Uso: make");
    }
}
