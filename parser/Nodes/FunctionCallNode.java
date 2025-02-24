package nodes;

import java.util.List;

public class FunctionCallNode extends Node {
    private IdentifierNode functionName;
    private List<Node> arguments;

    public FunctionCallNode(IdentifierNode functionName, List<Node> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public void print() {
        functionName.print();
        System.out.print("(");
        for (int i = 0; i < arguments.size(); i++) {
            arguments.get(i).print();
            if (i < arguments.size() - 1) System.out.print(", ");
        }
        System.out.println(");");
    }
}
