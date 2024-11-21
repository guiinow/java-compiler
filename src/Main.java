
import java.io.*;


public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("Uso: java Main <arquivo_de_entrada>");
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
