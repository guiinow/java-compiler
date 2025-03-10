package lang.ast.decl;

import java.util.List;

import lang.ast.LNode;
import lang.ast.types.*;
import lang.ast.LVisitor;

public class FunDef extends LNode {

    private String fname;
    private List<Param> params;
    private Block body;
    private List<Type> retrn;

    public FunDef(int l, int c, String fname, List<Param> params, List<Type> retrn, Block body) {
        super(l, c);
        this.fname = fname;
        this.params = params;
        this.retrn = retrn;
        this.body = body;
    }

    public String getFname() {
        return fname;
    }

    public List<Param> getParams() {
        return params;
    }

    public Block getBody() {
        return body;
    }

    public List<Type> getRetrn() {
        return retrn;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}
