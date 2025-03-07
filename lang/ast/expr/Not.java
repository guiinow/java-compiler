package lang.ast.expr;
import lang.ast.NodeVisitor;

public class Not extends Exp {

    private Exp value;
    public Not(int line, int col, Exp value) {
        super(line, col);
        this.value = value;
    }

    public Exp getExp() {
        return value;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
