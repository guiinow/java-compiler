package lang.ast.expr;
import lang.ast.NodeVisitor;

public class Float extends Exp {

    private float value;
    public Float(int line, int col, float value) {
        super(line, col);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
