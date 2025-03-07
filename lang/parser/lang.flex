
 * This lexer implementation contains Arrays as tokens.
 * The students are encouraged to argue why this is a bad ideia !

/* Lang lexer specification
 * Guilherme Lima 19.1.1276
 * Stephany xx.x.xxxx
 */

package lang.parser;

import java.util.ArrayList;
import java_cup.runtime.Symbol;

%%

%public
%function nextToken
%type Symbol
%class LangLexer

%line
%column

%unicode

%eofval{
    return new Symbol(LangParserSym.EOF, yyline + 1, yycolumn + 1);
%eofval}

%state ARR

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
            System.out.println("Impossible error converting " + s + " to float");
            return 0;
        }
    }

    private char ascIIToChar(String s) {
        String octalValue = s.substring(2, s.length() - 1); 
        try {
            int decimalValue = Integer.parseInt(octalValue, 8);
            return (char) decimalValue;
        } catch (NumberFormatException e) {
            throw new Error("Erro ao converter o valor octal '" + s + "' em um caractere ASCII.");
        }
    }

    private Symbol mkSymbol(int symCode, Object obj) {
        return new Symbol(symCode, yyline + 1, yycolumn + 1, obj);
    }

    private Symbol mkSymbol(int symCode) {
        return mkSymbol(symCode, null);
    }
%}

/* Macro Definitions */
identifier    = [a-z][a-zA-Z0-9_]*
type_id       = [A-Z][a-zA-Z0-9_]*
integer       = [0-9]+
float         = [0-9]+\.[0-9]*|\.[0-9]+|[0-9]+\.
whitespace    = [ \n\t\r]+ | {comment} 
escape        = "'\\b'" | "'\\n'" | "'\\t'" | "'\\r'"
ascII         = "'\\[0-9]{1,3}'"
comment       = "{-" ~"-}"

%%

<YYINITIAL> {
    /* Data types */
    "Int"            { return mkSymbol(LangParserSym.INT); }
    "Float"          { return mkSymbol(LangParserSym.FLOAT); }
    "Char"           { return mkSymbol(LangParserSym.CHAR); }
    "Bool"           { return mkSymbol(LangParserSym.BOOL); }

    /* Logical literals */
    "true"           { return mkSymbol(LangParserSym.TRUE, true); }
    "false"          { return mkSymbol(LangParserSym.FALSE, false); }

    /* Comments (ignored) */
    {comment}        { /* Ignore comments */ }

    /* Reserved words */
    "if"             { return mkSymbol(LangParserSym.IF); }
    "else"           { return mkSymbol(LangParserSym.ELSE); }
    "data"           { return mkSymbol(LangParserSym.DATA); }
    "null"           { return mkSymbol(LangParserSym.NULL); }
    "iterate"        { return mkSymbol(LangParserSym.ITERATE); }
    "read"           { return mkSymbol(LangParserSym.READ); }
    "print"          { return mkSymbol(LangParserSym.PRINT); }
    "return"         { return mkSymbol(LangParserSym.RETURN); }

    /* Operators */
    "=="             { return mkSymbol(LangParserSym.EQUAL); }
    "!="             { return mkSymbol(LangParserSym.DIFFERENT); }
    "<="             { return mkSymbol(LangParserSym.LESS_EQUAL); }
    ">="             { return mkSymbol(LangParserSym.GREATER_EQUAL); }
    "&&"             { return mkSymbol(LangParserSym.AND); }
    "::"             { return mkSymbol(LangParserSym.DOUBLE_COLON); }
    "="              { return mkSymbol(LangParserSym.ASSIGN); }
    "<"              { return mkSymbol(LangParserSym.LESS); }
    ">"              { return mkSymbol(LangParserSym.GREATER); }
    "+"              { return mkSymbol(LangParserSym.PLUS); }
    "-"              { return mkSymbol(LangParserSym.MINUS); }
    "*"              { return mkSymbol(LangParserSym.TIMES); }
    "/"              { return mkSymbol(LangParserSym.DIVIDE); }
    "%"              { return mkSymbol(LangParserSym.MOD); }
    "!"              { return mkSymbol(LangParserSym.NOT); }

    /* Punctuation symbols */
    "("              { return mkSymbol(LangParserSym.OPEN_PARENTHESIS); }
    ")"              { return mkSymbol(LangParserSym.CLOSE_PARENTHESIS); }
    "{"              { return mkSymbol(LangParserSym.OPEN_BRACES); }
    "}"              { return mkSymbol(LangParserSym.CLOSE_BRACES); }
    ";"              { return mkSymbol(LangParserSym.SEMICOLON); }
    ","              { return mkSymbol(LangParserSym.COMMA); }
    ":"              { return mkSymbol(LangParserSym.COLON); }
    "."              { return mkSymbol(LangParserSym.DOT); }
    "'"              { return mkSymbol(LangParserSym.QUOTATION_MARKS); }

    /* Identifiers */
    {identifier}     { return mkSymbol(LangParserSym.IDENTIFIER, yytext()); }
    {type_id}        { return mkSymbol(LangParserSym.TYPE_ID, yytext()); }

    /* Integer and Float literals */
    {integer}        { return mkSymbol(LangParserSym.INT_LITERAL, toInt(yytext())); }
    {float}          { return mkSymbol(LangParserSym.FLOAT_LITERAL, toFloat(yytext())); }

    /* Character literals */
    {escape}         { return mkSymbol(LangParserSym.ESCAPE, yytext()); }
    {ascII}          { return mkSymbol(LangParserSym.ASCII, ascIIToChar(yytext())); }

    /* Whitespace (ignored) */
    {whitespace}     { /* Ignore whitespaces */ }

    /* Array Handling */
    "["              { yybegin(ARR); arr = new ArrayList<>(); }

    /* Error handling for illegal characters */
    [^]              { throw new Error("Illegal character <" + yytext() + "> at line " + (yyline + 1) + ", column " + (yycolumn + 1)); }
}

/* Array Handling State */
<ARR> {
    {integer}       { arr.add(Integer.parseInt(yytext())); }
    {whitespace}    { /* Ignore whitespaces in ARR state */ }
    "]"             { yybegin(YYINITIAL); return mkSymbol(LangParserSym.ARR, arr); }
}


