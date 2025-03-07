/* 14/11/2024 - Calc lexer
 * Elton M. Cardoso
 * Example of a lexer for a simple claculator language.
 *
 * This lexer implementation contains Arrays as tokens.
 * The students are encouraged to argue why this is a bad ideia !
 *
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
       private ArrayList arr;

       private int toInt(String s){
          try{
              return Integer.parseInt(yytext());
          }catch(NumberFormatException e){
              System.out.println("erro de conversao: " + s + " para inteiro");
              return 0;
          }
       }

       private float toFloat(String s){
          try{
              return Float.parseFloat(yytext());
          }catch(NumberFormatException e){
              System.out.println("erro de conversao:  " + s + " para ponto flutuante");
              return 0;
          }
       }

       private Symbol mkSymbol(int symCode, Object obj){
           return new Symbol(symCode, yyline + 1, yycolumn + 1, obj);
       }

       private Symbol mkSymbol(int symCode){
           return mkSymbol(symCode,null);
       }

%}


identifier = [a-z]+
number = [0-9]+
white =  [ \n\t\r]+


%%
<YYINITIAL>{

"="            { return mkSymbol(LangParserSym.ATTR);    }
";"            { return mkSymbol(LangParserSym.SEMI);    }
"true"         { return mkSymbol(LangParserSym.TRUE, true);    }
"false"        { return mkSymbol(LangParserSym.FALSE, false);  }
"+"            { return mkSymbol(LangParserSym.PLUS);  }
"*"            { return mkSymbol(LangParserSym.TIMES);  }
"print"        { return mkSymbol(LangParserSym.PRINT);  }
{identifier}   { return mkSymbol(LangParserSym.ID,  yytext()); }
{number}       { return mkSymbol(LangParserSym.NUMBER, toInt(yytext()));   }

{white}        {/* While reading whites do nothing*/ }
[^]            {/* Matches any char form the input*/
                throw new Error("Illegal character <"+ yytext()+">"); }
}


