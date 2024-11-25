# Define variables
JFLEX = ./jflex-1.9.1/lib/jflex-full-1.9.1.jar
CUP = ./java-cup-bin-11b-20160615/java-cup-11b.jar
FLEX_SRC = src/lexical-analyzer.flex
LEXER_SRC = src/Lang_Lexer.java
CUP_SRC = ./jflex-1.9.1/examples/cup-interpreter/src/main/cup/parser.cup
BUILD_DIR = bin
MAIN_SRC = src/Main.java
CUP_LIB = ./java-cup-bin-11b-20160615/java-cup-11b-runtime.jar

# Default target
all: lexer parser compile

# Generate the lexer using JFlex
lexer: $(FLEX_SRC)
	java -jar $(JFLEX) -d src $(FLEX_SRC)
rqu
# Generate the parser using CUP
parser: $(CUP_SRC)
	java -jar $(CUP) -destdir $(BUILD_DIR) $(CUP_SRC)

# Compile all Java files including Main
compile:
	javac -cp $(CUP_LIB):$(BUILD_DIR) -d $(BUILD_DIR) src/sym.java src/parser.java $(LEXER_SRC) $(MAIN_SRC)

# Run the application
run:
	java -cp $(CUP_LIB):$(BUILD_DIR) Main input.txt

# Clean generated files (optional)
clean:
	rm -f src/Lang_Lexer.java
	rm -f src/sym.java src/parser.java
	rm -rf $(BUILD_DIR)/*.class
