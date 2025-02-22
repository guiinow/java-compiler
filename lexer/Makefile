# Autores:
# Guilherme Ferreira
# Breno Rotte

# Definição das ferramentas
JFLEX = ../tools/jflex.jar
CUP = ../tools/java-cup-11b.jar

# Arquivos do analisador léxico
LEXER_SRC = lang_lexer.flex
LEXER_GEN = LangLexer.java

# Arquivos do analisador sintático
PARSER_SRC = ../parser/lang.cup
PARSER_GEN = ../parser/LangParserSym.java ../parser/LangParser.java

# Arquivos Java principais
JAVA_FILES = Main.java $(LEXER_GEN) $(PARSER_GEN)

# Entrada padrão
INPUT_FILE = teste.txt  

# Regra padrão
all: lexer parser compile

# Geração do analisador léxico
lexer:
	java -jar $(JFLEX) -d ../parser $(LEXER_SRC)
	
# Geração do analisador sintático
parser:
	java -jar $(CUP) -destdir ../parser $(PARSER_SRC)

# Compilação
compile: $(LEXER_GEN) $(PARSER_GEN)
	javac $(JAVA_FILES)

# Limpeza
clean:
	rm -f Lang_lexer.java Lang_lexer.class Main.class parser.java sym.java *.java~ *.class

