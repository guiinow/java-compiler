import java.util.Arrays;

public class AST {
    public static void main(String[] args) {
        Node decl = new DeclarationNode(new TypeNode("Int"), new IdentifierNode("x"));
        Node assign = new AssignmentNode(new IdentifierNode("x"), new BinaryOperationNode(new LiteralNode(5), "+", new LiteralNode(10)));
        Node print = new PrintNode(new IdentifierNode("x"));

        BlockNode program = new BlockNode(Arrays.asList(decl, assign, print));
        program.print();
    }
}
