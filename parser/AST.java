import java.util.List;
import java.util.Arrays;

// Classe base para todos os nós da AST
abstract class Node {
    public abstract void print();
}

// Nó para representar números inteiros, floats e caracteres
class LiteralNode extends Node {
    private Object value;

    public LiteralNode(Object value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }
}

// Nó para representar identificadores (variáveis e funções)
class IdentifierNode extends Node {
    private String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.print(name);
    }
}

// Nó para representar tipos (Int, Float, Bool, Char, TYID)
class TypeNode extends Node {
    private String typeName;

    public TypeNode(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public void print() {
        System.out.print(typeName);
    }
}

// Nó para operações binárias (+, -, *, /, %, &&, ||, <, >, ==, !=)
class BinaryOperationNode extends Node {
    private Node left, right;
    private String operator;

    public BinaryOperationNode(Node left, String operator, Node right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print(" " + operator + " ");
        right.print();
        System.out.print(")");
    }
}

// Nó para atribuição de variável
class AssignmentNode extends Node {
    private IdentifierNode variable;
    private Node expression;

    public AssignmentNode(IdentifierNode variable, Node expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void print() {
        variable.print();
        System.out.print(" = ");
        expression.print();
        System.out.println(";");
    }
}

// Nó para declarações de variável
class DeclarationNode extends Node {
    private TypeNode type;
    private IdentifierNode variable;

    public DeclarationNode(TypeNode type, IdentifierNode variable) {
        this.type = type;
        this.variable = variable;
    }

    @Override
    public void print() {
        type.print();
        System.out.print(" ");
        variable.print();
        System.out.println(";");
    }
}

// Nó para estrutura de controle IF-ELSE
class IfNode extends Node {
    private Node condition;
    private Node thenBlock;
    private Node elseBlock;

    public IfNode(Node condition, Node thenBlock, Node elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public void print() {
        System.out.print("if ");
        condition.print();
        System.out.println(" {");
        thenBlock.print();
        System.out.println("}");
        if (elseBlock != null) {
            System.out.println("else {");
            elseBlock.print();
            System.out.println("}");
        }
    }
}

// Nó para estrutura de repetição WHILE (iterate)
class WhileNode extends Node {
    private Node condition;
    private Node body;

    public WhileNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void print() {
        System.out.print("while ");
        condition.print();
        System.out.println(" {");
        body.print();
        System.out.println("}");
    }
}

// Nó para o comando PRINT
class PrintNode extends Node {
    private Node expression;

    public PrintNode(Node expression) {
        this.expression = expression;
    }

    @Override
    public void print() {
        System.out.print("print(");
        expression.print();
        System.out.println(");");
    }
}

// Nó para retornos de função
class ReturnNode extends Node {
    private Node expression;

    public ReturnNode(Node expression) {
        this.expression = expression;
    }

    @Override
    public void print() {
        System.out.print("return ");
        expression.print();
        System.out.println(";");
    }
}

// Nó para blocos de código
class BlockNode extends Node {
    private List<Node> statements;

    public BlockNode(List<Node> statements) {
        this.statements = statements;
    }

    @Override
    public void print() {
        System.out.println("{");
        for (Node stmt : statements) {
            stmt.print();
        }
        System.out.println("}");
    }
}

// Nó para funções
class FunctionNode extends Node {
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

// Nó para chamadas de função
class FunctionCallNode extends Node {
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

// Classe principal para testar a AST
public class AST {
    public static void main(String[] args) {
        Node decl = new DeclarationNode(new TypeNode("Int"), new IdentifierNode("x"));
        Node assign = new AssignmentNode(new IdentifierNode("x"), new BinaryOperationNode(new LiteralNode(5), "+", new LiteralNode(10)));
        Node print = new PrintNode(new IdentifierNode("x"));

        BlockNode program = new BlockNode(Arrays.asList(decl, assign, print));
        program.print();
    }
}
