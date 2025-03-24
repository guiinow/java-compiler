package lang.ast.visitors.typeCheckVisitor;

public class CharType extends Type {
    @Override
    public boolean equals(Type other) {
        return other instanceof CharType;
    }

    @Override
    public String toString() {
        return "char";
    }
}