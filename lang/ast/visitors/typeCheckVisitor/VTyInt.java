package typeCheckVisitor;

public class VTyInt extends Type {

    private static VTyInt instance = null;
    private VTyInt() {
        super(CLTypes.INT);
    }
    public static VTyInt newInt() {
        if (instance == null) {
            instance = new VTyInt();
        }
        return instance;
    }
    public boolean match(Type t) {
        return getTypeValue() == t.getTypeValue();
    }

    @Override
    public String toString() {
        return "int";
    }
}