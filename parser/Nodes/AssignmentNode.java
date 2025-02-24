package nodes;

public class AssignmentNode extends Node {
    private IdentifierNode variable;
    private Node expression;

    public AssignmentNode(IdentifierNode variable, Node expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void print() {
        variable.print();
        System.out.print(" = ");
        expression.print();
        System.out.println(";");
    }
}
