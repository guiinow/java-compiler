public interface Visitor<R> {
    R visitExpressionNode(ExpressionNode node);
    R visitBinaryOperationNode(BinaryOperationNode node);
}

class ASTVisitor implements Visitor<Void> {
    @Override
    public Void visitExpressionNode(ExpressionNode node) {
        // Implement logic for visiting expression node
        System.out.println("Visiting expression node with value: " + node.value);
        return null;
    }

    @Override
    public Void visitBinaryOperationNode(BinaryOperationNode node) {
        // Implement logic for visiting binary operation node
        System.out.println("Visiting binary operation node with operator: " + node.operator);
        node.left.accept(this);
        node.right.accept(this);
        return null;
    }
}