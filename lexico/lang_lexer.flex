/* lang lexer specification
 * Guilherme Ferreira
 * Breno Rotte
 */

import java.util.ArrayList;

%%

%public
%function nextToken
%type Token
%class Lang_lexer

%line
%column

%unicode

%eofval{
    return new Token(yyline, yycolumn, TK.EOF);
%eofval}

%state ESCAPE

%{
    private ArrayList<Integer> arr;

    private int toInt(String s) {
      try {
          return Integer.parseInt(s);
      } catch (NumberFormatException e) {
          System.out.println("Impossible error converting " + s + " to integer");
          return 0;
      }
    }

    private float toFloat(String s) {
      try {
        return Float.parseFloat(s);
      } catch (NumberFormatException e) {
        System.out.println("Impossible error converting " + s + " to integer");
        return 0;
      }
    }

    private char ascIIToChar(String s) {

    String decimalValue = s.substring(2, s.length() - 1); 

    try {
        // Converte o valor octal para um inteiro e depois para char
        int decimal = Integer.parseInt(decimalValue);
        return (char) decimal;
    } catch (NumberFormatException e) {
        throw new Error("Erro ao converter o valor octal '" + s + "' em um caractere ASCII.");
    }
}

%}

/* Macro Definitions */
identifier    = [a-z][a-zA-Z0-9_]*
tyid          = [A-Z][a-zA-Z0-9_]*
integer       = [0-9]+
float         = [0-9]*\.[0-9]+
dots = \.\.
whitespace    = [ \n\t\r]+ | {comment} 
ascII = "'\\[0-9]{3}'"
comment = "{-" ~"-}"

%%

<YYINITIAL> {
    "--"  !([^]* \R [^]*) \R  {}

    /* Data types */
    "Int"            { return new Token(yyline, yycolumn, TK.INT); }
    "Float"          { return new Token(yyline, yycolumn, TK.FLOAT); }
    "Char"           { return new Token(yyline, yycolumn, TK.CHAR); }
    "Bool"           { return new Token(yyline, yycolumn, TK.BOOL); }

    /* Logical literals */
    "true"           { return new Token(yyline, yycolumn, TK.TRUE); }
    "false"          { return new Token(yyline, yycolumn, TK.FALSE); }

    /* Comments (ignored) */
    {comment}        { /* Ignore comments */ }

    /* Reserved words */
    "if"             { return new Token(yyline, yycolumn, TK.IF); }
    "else"           { return new Token(yyline, yycolumn, TK.ELSE); }
    "data"           { return new Token(yyline, yycolumn, TK.DATA); }
    "null"           { return new Token(yyline, yycolumn, TK.NULL); }
    "iterate"        { return new Token(yyline, yycolumn, TK.ITERATE); }
    "read"           { return new Token(yyline, yycolumn, TK.READ); }
    "print"          { return new Token(yyline, yycolumn, TK.PRINT); }
    "return"         { return new Token(yyline, yycolumn, TK.RETURN); }

    /* Operators */
    "=="             { return new Token(yyline, yycolumn, TK.EQUAL); }
    "!="             { return new Token(yyline, yycolumn, TK.DIFFERENT); }
    "<="             { return new Token(yyline, yycolumn, TK.LESS_EQUAL); }
    ">="             { return new Token(yyline, yycolumn, TK.GREATER_EQUAL); }
    "&&"             { return new Token(yyline, yycolumn, TK.AND); }
    "::"             { return new Token(yyline, yycolumn, TK.DOUBLE_COLON); }
    "="              { return new Token(yyline, yycolumn, TK.ASSIGN); }
    "<"              { return new Token(yyline, yycolumn, TK.LESS); }
    ">"              { return new Token(yyline, yycolumn, TK.GREATER); }
    "+"              { return new Token(yyline, yycolumn, TK.PLUS); }
    "-"              { return new Token(yyline, yycolumn, TK.MINUS); }
    "*"              { return new Token(yyline, yycolumn, TK.TIMES); }
    "/"              { return new Token(yyline, yycolumn, TK.DIVIDE); }
    "%"              { return new Token(yyline, yycolumn, TK.MOD); }
    "!"              { return new Token(yyline, yycolumn, TK.NOT); }

    /* Punctuation symbols */
    "("              { return new Token(yyline, yycolumn, TK.OPEN_PARENTHESIS); }
    ")"              { return new Token(yyline, yycolumn, TK.CLOSE_PARENTHESIS); }
    "{"              { return new Token(yyline, yycolumn, TK.OPEN_BRACES); }
    "}"              { return new Token(yyline, yycolumn, TK.CLOSE_BRACES); }
    ";"              { return new Token(yyline, yycolumn, TK.SEMICOLON); }
    ","              { return new Token(yyline, yycolumn, TK.COMMA); }
    ":"              { return new Token(yyline, yycolumn, TK.COLON); }
    "."              { return new Token(yyline, yycolumn, TK.DOT); }
    "'"              { return new Token(yyline, yycolumn, TK.QUOTATION_MARKS); }

    /* Identifiers */
    {identifier}     { return new Token(yyline, yycolumn, TK.IDENTIFIER, yytext()); }

    /* Integer and Float literals */
    {integer}        { return new Token(yyline, yycolumn, TK.INT_LITERAL, Integer.parseInt(yytext())); }
    {float}          { return new Token(yyline, yycolumn, TK.FLOAT_LITERAL, Float.parseFloat(yytext())); }
    \.             { return new Token(yyline, yycolumn, TK.DOT); }
    "\'\\"       { yybegin(ESCAPE);}
    {ascII}        { return new Token(yyline, yycolumn, TK.ASCII, ascIIToChar(yytext())); }

    /* Whitespace (ignored) */
    {whitespace}     { /* Ignore whitespaces */ }

    "["            { return new Token(yyline, yycolumn, TK.OPEN_BRACKETS); }
    "]"            { return new Token(yyline, yycolumn, TK.CLOSE_BRACKETS); }

    /* Error handling for illegal characters */
    [^]              { throw new Error("Illegal character <" + yytext() + "> at line " + yyline + ", column " + yycolumn); }
}

/* Array Handling State */
<ESCAPE> {
"n\'" { yybegin (YYINITIAL); return new Token(yyline, yycolumn, TK.ESCAPE, '\n'); }
"t\'" { yybegin (YYINITIAL); return new Token(yyline, yycolumn, TK.ESCAPE, '\t'); }
"b\'" { yybegin (YYINITIAL); return new Token(yyline, yycolumn, TK.ESCAPE, '\b'); }
"r\'" { yybegin (YYINITIAL); return new Token(yyline, yycolumn, TK.ESCAPE, '\r'); }
"\\\'" { yybegin (YYINITIAL); return new Token(yyline, yycolumn, TK.ESCAPE, '\\'); }
}
