# Definição de variáveis
JAVAC = javac
JAVA = java
SRC_AST = AST.java
CLASS_AST = AST
SRC_PARSER = *.java
CLASS_PARSER = Parser

# Regra para compilar tudo (parser completo)
all:
	$(JAVAC) $(SRC_PARSER)

# Regra para rodar o parser completo
run: all
	$(JAVA) $(CLASS_PARSER)

# Regra para compilar apenas o AST
AST_compile:
	$(JAVAC) $(SRC_AST)

# Regra para rodar apenas o AST
AST: AST_compile
	$(JAVA) $(CLASS_AST)

# Regra para limpar arquivos .class
clean:
	rm -f *.class
