/* lang lexer specification
 * Guilherme Ferreira
 * Breno Rotte
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
        String decimalValue = s.substring(2, s.length() - 1);
        try {
            int decimal = Integer.parseInt(decimalValue);
            return (char) decimal;
        } catch (NumberFormatException e) {
            throw new Error("Erro ao converter o valor inteiro '" + s + "' em um caractere ASCII.");
        }
    }
%}

/* Macro Definitions */
identifier    = [a-z][a-zA-Z0-9_-]*
tyid          = [A-Z][a-zA-Z0-9_]*
integer       = [0-9]*
float         = [0-9]*\.[0-9]+
dots          = \.. 
whitespace    = [ \n\t\r]+ | {comment}
ascII         = "'\\[0-9]{3}'"
comment       = "{-" ~"-}"

%%

<YYINITIAL> {
    "--"  !([^]* \R [^]*) \R  {}

    /* Data types */
    "Int"            { return new Symbol(LangParserSym.INT, yyline + 1, yycolumn + 1); }
    "Float"          { return new Symbol(LangParserSym.FLOAT, yyline + 1, yycolumn + 1); }
    "Char"           { return new Symbol(LangParserSym.CHAR, yyline + 1, yycolumn + 1); }
    "Bool"           { return new Symbol(LangParserSym.BOOL, yyline + 1, yycolumn + 1); }

    /* Logical literals */
    "true"           { return new Symbol(LangParserSym.TRUE, yyline + 1, yycolumn + 1, true); }
    "false"          { return new Symbol(LangParserSym.FALSE, yyline + 1, yycolumn + 1, false); }

    /* Comments (ignored) */
    {comment}        { /* Ignore comments */ }

    /* Reserved words */
    "if"             { return new Symbol(LangParserSym.IF, yyline + 1, yycolumn + 1); }
    "else"           { return new Symbol(LangParserSym.ELSE, yyline + 1, yycolumn + 1); }
    "data"           { return new Symbol(LangParserSym.DATA, yyline + 1, yycolumn + 1); }
    "null"           { return new Symbol(LangParserSym.NULL, yyline + 1, yycolumn + 1); }

    /* Identifiers and literals */
    {identifier}      { return new Symbol(LangParserSym.IDENTIFIER, yyline + 1, yycolumn + 1, yytext()); }
    {integer}         { return new Symbol(LangParserSym.INT_LITERAL, yyline + 1, yycolumn + 1, toInt(yytext())); }
    {float}           { return new Symbol(LangParserSym.FLOAT_LITERAL, yyline + 1, yycolumn + 1, toFloat(yytext())); }
    {ascII}           { return new Symbol(LangParserSym.ASCII, yyline + 1, yycolumn + 1, ascIIToChar(yytext())); }

    /* Whitespace (ignored) */
    {whitespace}      { /* Ignore whitespaces */ }

    /* Operators and punctuation */
    "=="             { return new Symbol(LangParserSym.EQUAL, yyline + 1, yycolumn + 1); }
    "!="             { return new Symbol(LangParserSym.DIFFERENT, yyline + 1, yycolumn + 1); }
    "="              { return new Symbol(LangParserSym.ASSIGN, yyline + 1, yycolumn + 1); }
    "<"              { return new Symbol(LangParserSym.LESS, yyline + 1, yycolumn + 1); }
    ">"              { return new Symbol(LangParserSym.GREATER, yyline + 1, yycolumn + 1); }
    "+"              { return new Symbol(LangParserSym.PLUS, yyline + 1, yycolumn + 1); }
    "-"              { return new Symbol(LangParserSym.MINUS, yyline + 1, yycolumn + 1); }
    "("              { return new Symbol(LangParserSym.OPEN_PARENTHESIS, yyline + 1, yycolumn + 1); }
    ")"              { return new Symbol(LangParserSym.CLOSE_PARENTHESIS, yyline + 1, yycolumn + 1); }
    "["              { return new Symbol(LangParserSym.OPEN_BRACKETS, yyline + 1, yycolumn + 1); }
    "]"              { return new Symbol(LangParserSym.CLOSE_BRACKETS, yyline + 1, yycolumn + 1); }
    "{"              { return new Symbol(LangParserSym.OPEN_BRACES, yyline + 1, yycolumn + 1); }
    "}"              { return new Symbol(LangParserSym.CLOSE_BRACES, yyline + 1, yycolumn + 1); }
    "\""            { return new Symbol(LangParserSym.QUOTATION_MARKS, yyline + 1, yycolumn + 1); }

    /* Error handling for illegal characters */
    [^]              { throw new Error("Illegal character <" + yytext() + "> at line " + yyline + ", column " + yycolumn); }
}
