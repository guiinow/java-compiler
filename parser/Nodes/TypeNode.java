package parser.nodes;

public class TypeNode extends Node {
    private String typeName;

    public TypeNode(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public void print() {
        System.out.print(typeName);
    }
}
