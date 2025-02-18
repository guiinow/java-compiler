// Autores:
// Guilherme Ferreira
// Breno Rotte

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java_cup.runtime.Symbol;
import parser.Parser;
import parser.sym;

public class Main {

    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public static void main(String[] args) {
        if (args.length != 1) {
            help();
            System.exit(0);
        }

        try {
            // Inicializa o analisador léxico
            Lang_lexer lexer = new Lang_lexer(new FileReader(args[0]));

            // Processa os tokens e imprime
            Token token = lexer.nextToken();
            while (token.tk != TK.EOF) {
                System.out.println(token.toString());
                token = lexer.nextToken();
            }

            // Executa o parser
            System.out.println("\nIniciando análise sintática...");
            FileReader file = new FileReader(args[0]);
            Lang_lexer lex = new Lang_lexer(file);
            Parser parser = new Parser(lex);
            Symbol result = parser.parse();

            System.out.println("Análise sintática concluída com sucesso!");

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
