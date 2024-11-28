# Autores:
# Guilherme Ferreira
# Breno Rotte

# Makefile to compile and run JFlex lexer

JFLEX = jflex.jar
LEXER_SRC = lang_lexer.flex
JAVA_FILES = Main.java Lang_lexer.java
INPUT_FILE = teste.txt  # Arquivo de entrada padrão

# Default target to generate the lexer, compile, and run
all: lexer compile run

# Generate lexer using JFlex
lexer:
	java -jar $(JFLEX) $(LEXER_SRC)

# Compile the Java files
compile: Lang_lexer.java
	javac $(JAVA_FILES)

# Run the main program with a specified input file
run: 
	java Main $(INPUT_FILE)

# Clean generated files
clean:
	rm -f Lang_lexer.java Lang_lexer.class Main.class *.java~ *.class



# Makefile to compile and run JFlex lexer

JFLEX = jflex.jar
LEXER_SRC = lang_lexer.flex
JAVA_FILES = Main.java Lang_lexer.java

# Default target to generate the lexer, compile, and run
all: lexer compile

# Generate lexer using JFlex
lexer:
	java -jar $(JFLEX) $(LEXER_SRC)

# Compile the Java files
compile: Lang_lexer.java
	javac $(JAVA_FILES)

# Run the main program with a dynamically specified input file
%.txt: all
	java Main $@

# Clean generated files
clean:
	rm -f Lang_lexer.java Lang_lexer.class Main.class *.java~ *.class

