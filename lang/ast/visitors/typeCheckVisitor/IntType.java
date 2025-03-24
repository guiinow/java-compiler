package typeCheckVisitor;

public class IntType extends Type {
    @Override
    public boolean equals(Type other) {
        return other instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}