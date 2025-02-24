package nodes;

import java.util.List;

public class BlockNode extends Node {
    private List<Node> statements;

    public BlockNode(List<Node> statements) {
        this.statements = statements;
    }

    @Override
    public void print() {
        System.out.println("{");
        for (Node stmt : statements) {
            stmt.print();
        }
        System.out.println("}");
    }
}
