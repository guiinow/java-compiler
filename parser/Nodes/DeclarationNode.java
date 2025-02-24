package nodes;

public class DeclarationNode extends Node {
    private TypeNode type;
    private IdentifierNode variable;

    public DeclarationNode(TypeNode type, IdentifierNode variable) {
        this.type = type;
        this.variable = variable;
    }

    @Override
    public void print() {
        type.print();
        System.out.print(" ");
        variable.print();
        System.out.println(";");
    }
}
