# Definição de variáveis
JAVAC = javac
JAVA = java
SRC_AST = AST.java
CLASS_AST = AST
SRC_PARSER = *.java
CLASS_PARSER = Parser
OUTPUT_DIR = Nodes

# Regra para compilar tudo (parser completo)
all:
	$(JAVAC) -d $(OUTPUT_DIR) $(SRC_PARSER)

# Regra para rodar o parser completo
run: all
	$(JAVA) -cp $(OUTPUT_DIR) $(CLASS_PARSER)

# Regra para compilar apenas o AST
AST_compile:
	$(JAVAC) -d $(OUTPUT_DIR) $(SRC_AST)

# Regra para rodar apenas o AST
AST: AST_compile
	$(JAVA) -cp $(OUTPUT_DIR) $(CLASS_AST)

# Regra para limpar arquivos .class
clean:
	rm -f $(OUTPUT_DIR)/*.class