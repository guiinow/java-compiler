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
