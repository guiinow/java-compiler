
public abstract class ASTNode {
    public abstract <R> R accept(Visitor<R> visitor);
}

class BinaryOperationNode extends ASTNode {
    public final ASTNode left;
    public final ASTNode right;
    public final String operator;

    public BinaryOperationNode(ASTNode left, ASTNode right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitBinaryOperationNode(this);
    }
}