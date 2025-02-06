import lexico.Token;
import lexico.TK;
import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ASTNode parse() {
        return parseExpression();
    }

    private ASTNode parseExpression() {
        // Example parsing logic for a binary operation
        ASTNode left = parsePrimary();

        while (match(TK.PLUS, TK.MINUS)) {
            Token operator = previous();
            ASTNode right = parsePrimary();
            left = new BinaryOperationNode(left, right, operator.lexeme);
        }

        return left;
    }

    private ASTNode parsePrimary() {
        if (match(TK.INT_LITERAL)) {
            return new ExpressionNode(previous().lexeme);
        }

        throw new RuntimeException("Expected expression.");
    }

    private boolean match(TK... types) {
        for (TK type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TK type) {
        if (isAtEnd()) return false;
        return peek().tk == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().tk == TK.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}