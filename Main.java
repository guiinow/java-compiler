import java.io.FileReader;
import java.io.IOException;
// tem de importar java-cup aqui, eu suponho

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("Uso: java Main <arquivo_de_entrada>"); // alterar aqui
                return;
            }

            FileReader fileReader = new FileReader(args[0]);
            Lang_Lexer lexer = new Lang_Lexer(fileReader);

            Symbol token;
            while ((token = lexer.next_token()).sym != sym.EOF) {
                System.out.println("Token: " + token.toString());
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
###############################################################################################

import java.io.FileReader;
import java.io.FileNotFoundException;
import java_cup.runtime.Symbol; 

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            help();
            System.exit(0);
        }

        try {
            Lang_Lexer lexer = new Lang_Lexer(new FileReader(args[0]));
            Symbol token = lexer.next_token();

            while (token.sym != sym.EOF) {
                System.out.println("Token: " + token.toString());
                token = lexer.next_token();
            }

            System.out.println("Token: " + token.toString());

        } catch (FileNotFoundException e) {
            System.out.println("O arquivo " + args[0] + " não foi encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao processar o arquivo " + args[0] + ".");
            e.printStackTrace();
        }
    }

    //Exibe informações de ajuda sobre como usar o programa.
    public static void help() {
        System.out.println("Analisador léxico para a linguagem Lang.");
        System.out.println("Versão: 1.0");
        System.out.println("Uso: java Main <nome-do-arquivo>");
        System.out.println("  <nome-do-arquivo> : Caminho relativo ou absoluto");
        System.out.println("                     para um arquivo contendo código na linguagem Lang.");
    }
}
