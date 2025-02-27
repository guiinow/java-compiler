package parser.nodes;

import java.util.List;

public class FunctionNode extends Node {
    private TypeNode returnType;
    private IdentifierNode name;
    private List<IdentifierNode> parameters;
    private BlockNode body;

    public FunctionNode(TypeNode returnType, IdentifierNode name, List<IdentifierNode> parameters, BlockNode body) {
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public void print() {
        returnType.print();
        System.out.print(" ");
        name.print();
        System.out.print("(");
        for (int i = 0; i < parameters.size(); i++) {
            parameters.get(i).print();
            if (i < parameters.size() - 1) System.out.print(", ");
        }
        System.out.println(") {");
        body.print();
        System.out.println("}");
    }
}
