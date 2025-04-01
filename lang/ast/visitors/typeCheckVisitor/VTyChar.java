package lang.ast.visitors.typeCheckVisitor;

public class VTyChar extends Type {

    private static VTyChar instance = null;
    private VTyChar() {
        super(CLTypes.CHAR);
    }
    public static VTyChar newChar() {
        if (instance == null) {
            instance = new VTyChar();
        }
        return instance;
    }
    public boolean match(Type t) {
        return getTypeValue() == t.getTypeValue();
    }

    @Override
    public String toString() {
        return "char";
    }
}