package lang.ast.visitors.typeCheckVisitor;

public class TypeError extends VType {
    public TypeError(short message) {
        super(message);
    }

    @Override
    public boolean match(VType t) {
        return true; 
    }
}