public class ExpressionNode extends ASTNode {
    public final String value;

    public ExpressionNode(String value) {
        this.value = value;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitExpressionNode(this);
    }
}