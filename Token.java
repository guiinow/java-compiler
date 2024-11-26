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


###############################################################################################

public class Token {

    // conferir se o código abaixo é válido
    
    private String token;
    private EnumToken type; //Mudei String para EnumToken para evitar erros de digitação

    public Token(String token, EnumToken type) {
        // Validação para evitar valores nulos
        if (token == null || type == null) {
            throw new IllegalArgumentException("Token e Type não podem ser nulos.");
        }
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public EnumToken getType() {
        return type;
    }

    public String toString() {
        return "Token: " + token + " Type: " + type;
    }
}
