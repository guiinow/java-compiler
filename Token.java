public class Token {

    // conferir se o código abaixo é válido
    
    private String token;
    private String type;

    public Token(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return "Token: " + token + " Type: " + type;
    }
}
