 java -jar /home/guiinow/Dropbox/eng-computacao-ufop/24.2/compiladores/jflex-1.9.1/lib/jflex-full-1.9.1.jar src/lexical-analyzer.flex

 javac src/Lang_Lexer.java

                         ^
  symbol:   class Symbol
  location: class Lang_Lexer
src/Lang_Lexer.java:978: error: cannot find symbol
            { return new Symbol(sym.ITERATE);
                                ^
  symbol:   variable sym
  location: class Lang_Lexer
87 errors

 sudo java -jar java-cup-bin-11b-20160615/java-cup-11b.jar  /home/guiinow/Dropbox/eng-computacao-ufop/24.2/compiladores/jflex-1.9.1/examples/cup-interpreter/src/main/cup/parser.cup