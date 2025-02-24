package nodes;

public class WhileNode extends Node {
    private Node condition;
    private Node body;

    public WhileNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void print() {
        System.out.print("while ");
        condition.print();
        System.out.println(" {");
        body.print();
        System.out.println("}");
    }
}
