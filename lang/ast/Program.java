package lang.ast;

import java.util.List;

import lang.ast.decl.*;
import lang.ast.reserved.Data;

public class Program extends LNode {
    private List<FunDef> funcs;
    private List<Data> dat;

    public Program(int l, int c, List<Data> dat, List<FunDef> fs) {
        super(l, c);
        this.dat = dat;
        this.funcs = fs;
    }

    public List<Data> getData() {
        return dat;
    }

    public List<FunDef> getFuncs() {
        return funcs;
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}
