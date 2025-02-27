package parser.nodes;

public class IfNode extends Node {
    private Node condition;
    private Node thenBlock;
    private Node elseBlock;

    public IfNode(Node condition, Node thenBlock, Node elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public void print() {
        System.out.print("if ");
        condition.print();
        System.out.println(" {");
        thenBlock.print();
        System.out.println("}");
        if (elseBlock != null) {
            System.out.println("else {");
            elseBlock.print();
            System.out.println("}");
        }
    }
}
