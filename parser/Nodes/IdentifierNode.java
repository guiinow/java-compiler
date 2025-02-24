package nodes;

public class IdentifierNode extends Node {
    private String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.print(name);
    }
}
