package lang.ast.visitors.typeCheckVisitor;

import lang.ast.*;

public abstract class VType {
     public short type;
     protected VType(short type){ this.type = type;}
     public abstract boolean match(VType t);
     public short getTypeValue(){ return type;}
}
