package lang.ast.visitors.typeCheckVisitor;

import java.util.Hashtable;

public class TypeEntry {
    public String sym;
    public VType ty;
    public Hashtable<String, VType> localCtx;
}
