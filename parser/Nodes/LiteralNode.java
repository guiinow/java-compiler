package parser.nodes;

public class LiteralNode extends Node {
    private Object value;

    public LiteralNode(Object value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }
}
