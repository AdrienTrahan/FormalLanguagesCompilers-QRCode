import java.io.*;
import java_cup.runtime.*;

%%

%unicode
%cup
%line
%column

    
nl = \n|\r|\n\r

NAME = [a-zA-Z_][a-zA-Z0-9_]*
SCALAR_VALUE = [+-]?(([0-9]+(\.[0-9]*)?)|(\.[0-9]+))([eE][+-]?[0-9]+)?
STRING_VALUE = \"([^\\\"\n\r]|\\.)*\"

PLUS = "+"
MINUS = "-"
MUL = "*"
DIV = "/"

AND = "&&"
OR = "||"
EQUALITY = "=="
INEQUALITY = "!="
GREATER = ">"
LESS = "<"
GREATER_EQUAL = ">="
LESS_EQUAL = "<="


ASSIGNMENT = "="
PLUS_ASSIGNMENT = "+="
MINUS_ASSIGNMENT = "-="
MUL_ASSIGNMENT = "*="
DIV_ASSIGNMENT = "/="

IF = "if"
THEN = "then"
ELSE = "else"
WHILE = "while"
FUNCTION = "function"
TRUE = "true"
FALSE = "false"
OPEN_PARENTHESIS = "("
CLOSE_PARENTHESIS = ")"
OPEN_CURLY_BRACKET = "{"
CLOSE_CURLY_BRACKET = "}"
SEMICOLON = ";"
DOT = "."
NOT = "!"


%%


{IF}                  { return new Symbol(sym.IF, yyline, yycolumn); }
{THEN}                { return new Symbol(sym.THEN, yyline, yycolumn); }
{ELSE}                { return new Symbol(sym.ELSE, yyline, yycolumn); }
{WHILE}               { return new Symbol(sym.WHILE, yyline, yycolumn); }
{FUNCTION}            { return new Symbol(sym.FUNCTION, yyline, yycolumn); }
{OPEN_PARENTHESIS}    { return new Symbol(sym.OPEN_PARENTHESIS, yyline, yycolumn); }
{CLOSE_PARENTHESIS}   { return new Symbol(sym.CLOSE_PARENTHESIS, yyline, yycolumn); }
{OPEN_CURLY_BRACKET}  { return new Symbol(sym.OPEN_CURLY_BRACKET, yyline, yycolumn); }
{CLOSE_CURLY_BRACKET} { return new Symbol(sym.CLOSE_CURLY_BRACKET, yyline, yycolumn); }
{SEMICOLON}           { return new Symbol(sym.SEMICOLON, yyline, yycolumn); }
{DOT}                 { return new Symbol(sym.DOT, yyline, yycolumn); }
{NOT}                 { return new Symbol(sym.NOT, yyline, yycolumn); }

{AND}                 { return new Symbol(sym.AND, yyline, yycolumn); }
{OR}                  { return new Symbol(sym.OR, yyline, yycolumn); }
{EQUALITY}            { return new Symbol(sym.EQUALITY, yyline, yycolumn); }
{INEQUALITY}          { return new Symbol(sym.INEQUALITY, yyline, yycolumn); }
{GREATER}             { return new Symbol(sym.GREATER, yyline, yycolumn); }
{LESS}                { return new Symbol(sym.LESS, yyline, yycolumn); }
{GREATER_EQUAL}       { return new Symbol(sym.GREATER_EQUAL, yyline, yycolumn); }
{LESS_EQUAL}          { return new Symbol(sym.LESS_EQUAL, yyline, yycolumn); }


{ASSIGNMENT}          { return new Symbol(sym.ASSIGNMENT, yyline, yycolumn); }
{PLUS_ASSIGNMENT}     { return new Symbol(sym.PLUS_ASSIGNMENT, yyline, yycolumn); }
{MINUS_ASSIGNMENT}    { return new Symbol(sym.MINUS_ASSIGNMENT, yyline, yycolumn); }
{MUL_ASSIGNMENT}      { return new Symbol(sym.MUL_ASSIGNMENT, yyline, yycolumn); }
{DIV_ASSIGNMENT}      { return new Symbol(sym.DIV_ASSIGNMENT, yyline, yycolumn); }

{PLUS}                { return new Symbol(sym.PLUS, yyline, yycolumn); }
{MINUS}               { return new Symbol(sym.MINUS, yyline, yycolumn); }
{MUL}                 { return new Symbol(sym.MUL, yyline, yycolumn); }
{DIV}                 { return new Symbol(sym.DIV, yyline, yycolumn); }


{TRUE}                { return new Symbol(sym.BOOL_VALUE, yyline, yycolumn, Boolean.valueOf(true)); }
{FALSE}               { return new Symbol(sym.BOOL_VALUE, yyline, yycolumn, Boolean.valueOf(false)); }
{SCALAR_VALUE}        { return new Symbol(sym.SCALAR_VALUE, yyline, yycolumn, Float.valueOf(yytext())); }
{STRING_VALUE} {
    String raw = yytext();
    String inner = raw.substring(1, raw.length() - 1);
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < inner.length(); i++) {
        char c = inner.charAt(i);
        if (c == '\\' && i + 1 < inner.length()) {
            char next = inner.charAt(++i);
            switch (next) {
                case '"':  sb.append('"'); break;
                case '\\': sb.append('\\'); break;
                case 'n':  sb.append('\n'); break;
                case 'r':  sb.append('\r'); break;
                case 't':  sb.append('\t'); break;
                default:   sb.append(next); break;
            }
        } else {
            sb.append(c);
        }
    }
    return new Symbol(sym.STRING_VALUE, yyline, yycolumn, sb.toString());
}
{NAME}                { return new Symbol(sym.NAME, yyline, yycolumn, new String(yytext())); }

{nl}|" "|\t     { ; } 