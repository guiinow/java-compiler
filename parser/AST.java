// Definição da classe base para todos os nós da AST
abstract class Node {
    public abstract void print();
}

// Nó para representar um número inteiro
class NumberNode extends Node {
    private int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }
}

// Nó para representar um identificador
class IdentifierNode extends Node {
    private String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void print() {
        System.out.print(name);
    }
}

// Nó para operações matemáticas (+, -, *, /)
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

// Nó para atribuição de variável (exemplo: x = 5 + 10)
class AssignmentNode extends Node {
    private IdentifierNode variable;
    private Node expression;

    public AssignmentNode(IdentifierNode variable, Node expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void print() {
        System.out.print(variable.getName() + " = ");
        expression.print();
        System.out.println(";");
    }
}

// Nó para operações booleanas (==, !=, &&, ||, <, <=, >, >=)
class BooleanOperationNode extends Node {
    private Node left, right;
    private String operator;

    public BooleanOperationNode(Node left, String operator, Node right) {
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

// Nó para estrutura de controle IF
class IfNode extends Node {
    private Node condition;
    private Node body;

    public IfNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void print() {
        System.out.print("if ");
        condition.print();
        System.out.println(" {");
        body.print();
        System.out.println("}");
    }
}

// Nó para estrutura de repetição WHILE
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

// Nó para print
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

// Nó para blocos de código
class BlockNode extends Node {
    private Node[] statements;

    public BlockNode(Node... statements) {
        this.statements = statements;
    }

    @Override
    public void print() {
        for (Node stmt : statements) {
            stmt.print();
        }
    }
}

// Classe principal para testar a AST
public class AST {
    public static void main(String[] args) {
        // Criando a AST para a expressão: x = (5 + 10) * 2;
        Node expr = new BinaryOperationNode(
            new BinaryOperationNode(new NumberNode(5), "+", new NumberNode(10)),
            "*",
            new NumberNode(2)
        );

        Node assignment = new AssignmentNode(new IdentifierNode("x"), expr);

        // Criando um IF: if (x > 0) { x = x - 1; }
        Node condition = new BooleanOperationNode(new IdentifierNode("x"), ">", new NumberNode(0));
        Node ifBody = new AssignmentNode(new IdentifierNode("x"), new BinaryOperationNode(new IdentifierNode("x"), "-", new NumberNode(1)));
        Node ifStatement = new IfNode(condition, ifBody);

        // Criando um WHILE: while (x > 0) { x = x - 1; }
        Node whileStatement = new WhileNode(condition, ifBody);

        // Criando um bloco de código com todas as instruções
        Node program = new BlockNode(assignment, ifStatement, whileStatement);

        // Exibir a AST formatada
        program.print();
    }
}
