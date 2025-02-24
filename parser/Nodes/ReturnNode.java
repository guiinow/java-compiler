package nodes;

public class ReturnNode extends Node {
    private Node expression;

    public ReturnNode(Node expression) {
        this.expression = expression;
    }

    @Override
    public void print() {
        System.out.print("return ");
        expression.print();
        System.out.println(";");
    }
}
