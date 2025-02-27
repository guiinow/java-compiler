package parser.nodes;

public class PrintNode extends Node {
    private Node expression;

    public PrintNode(Node expression) {
        this.expression = expression;
    }

    @Override
    public void print() {
        System.out.print("print(");
        expression.print();
        System.out.println(");");
    }
}
