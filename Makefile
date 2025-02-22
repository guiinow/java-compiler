# Autores:
# Guilherme Ferreira
# Breno Rotte

# Regra padrão
all: lexer parser compile

# Geração do analisador léxico
parser/LangLexer.java:
	java -jar tools/jflex.jar -d parser parser/lang_lexer.flex
	
# Geração do analisador sintático
parser/LangParser.java:
	java -jar tools/java-cup-11b.jar -destdir parser parser/lang.cup

# Compilação
compile: parser/LangLexer.java parser/LangParser.java
	javac -cp .:tools/java-cup-11b.jar Main.java

# Limpeza
clean:
	find -name "*.class" -delete
	rm -f parser/LangLexer.java parser/LangParser.java 

