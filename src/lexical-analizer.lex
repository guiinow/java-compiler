/* lang lexer specification
 * eira
 * Breno Rotte
 */
import java_cup.runtime.*;

%%

%class Lang_Lexer
%unicode 
%cup
%line
%column

%%

/* data types */

<YYINITIAL> "Int" { return new Symbol(sym.INT); }
<YYINITIAL> "Float" { return new Symbol(sym.FLOAT); }
<YYINITIAL> "Char" { return new Symbol(sym.CHAR); }
<YYINITIAL> "Bool" { return new Symbol(sym.BOOL); }

/* logical literal */

<YYINITIAL> "true" { return new Symbol(sym.TRUE); }
<YYINITIAL> "false" { return new Symbol(sym.FALSE); }

/* comments */

<YYINITIAL> "--".* { /* ignore comments */ }
<YYINITIAL> "{-".*"-}" { /* ignore comments */ }

/* reserved words */

<YYINITIAL> "if" { return new Symbol(sym.IF); }
<YYINITIAL> "else" { return new Symbol(sym.ELSE); }
<YYINITIAL> "data" { return new Symbol(sym.DATA); }
<YYINITIAL> "null" { return new Symbol(sym.NULL); }
<YYINITIAL> "iterate" { return new Symbol(sym.ITERATE); }
<YYINITIAL> "read" { return new Symbol(sym.READ); }
<YYINITIAL> "print" { return new Symbol(sym.PRINT); }
<YYINITIAL> "return" { return new Symbol(sym.RETURN); }

/* operators */

<YYINITIAL> "+" { return new Symbol(sym.PLUS); }
<YYINITIAL> "-" { return new Symbol(sym.MINUS); }
<YYINITIAL> "*" { return new Symbol(sym.TIMES); }
<YYINITIAL> "/" { return new Symbol(sym.DIVIDE); }
<YYINITIAL> "=" { return new Symbol(sym.ASSIGN); }
<YYINITIAL> "==" { return new Symbol(sym.EQUAL); }
<YYINITIAL> "!=" { return new Symbol(sym.DIFFERENT); }
<YYINITIAL> "<" { return new Symbol(sym.LESS); }
<YYINITIAL> "<=" { return new Symbol(sym.LESS_EQUAL); }
<YYINITIAL> ">" { return new Symbol(sym.GREATER); }
<YYINITIAL> ">=" { return new Symbol(sym.GREATER_EQUAL); }
<YYINITIAL> "&&" { return new Symbol(sym.AND); }
<YYINITIAL> "!" { return new Symbol(sym.NOT); }
<YYINITIAL> "(" { return new Symbol(sym.OPEN_PARENTHESIS); }
<YYINITIAL> ")" { return new Symbol(sym.CLOSE_PARENTHESIS); }
<YYINITIAL> "{" { return new Symbol(sym.OPEN_BRACES); }
<YYINITIAL> "}" { return new Symbol(sym.CLOSE_BRACES); }
<YYINITIAL> ";" { return new Symbol(sym.SEMICOLON); }
<YYINITIAL> "," { return new Symbol(sym.COMMA); }
<YYINITIAL> ":" { return new Symbol(sym.COLON); }
<YYINITIAL> "." { return new Symbol(sym.DOT); }
<YYINITIAL> "::" { return new Symbol(sym.DOUBLE_COLON); }
<YYINITIAL> "%" { return new Symbol(sym.MOD); }

