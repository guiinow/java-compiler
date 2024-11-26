import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public static void main(String[] args) {
        if (args.length != 1) {
            help();
            System.exit(0);
        }

        try {
            Lang_lexer lexer = new Lang_lexer(new FileReader(args[0]));
           Token token = lexer.nextToken();
          while(token.tk != TK.EOF){
              System.out.println(token.toString());
              token = lexer.nextToken();
          }


            System.out.println("Token: " + token.toString());

        } catch (FileNotFoundException e) {
            System.out.println("O arquivo " + args[0] + " não foi encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao processar o arquivo " + args[0] + ".");
            e.printStackTrace();
        }
    }


    public static void help() {
        System.out.println("Analisador léxico para a linguagem Lang.");
        System.out.println("Versão: 1.0");
        System.out.println("Uso: make");
    }
}
