package nodes;

public class BinaryOperationNode extends Node {
    private Node left, right;
    private String operator;

    public BinaryOperationNode(Node left, String operator, Node right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print(" " + operator + " ");
        right.print();
        System.out.print(")");
    }
}
