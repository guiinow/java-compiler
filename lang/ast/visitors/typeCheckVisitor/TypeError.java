package typeCheckVisitor;

public class TypeError extends RuntimeException {
    public TypeError(String message) {
        super(message);
    }
}