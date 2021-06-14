package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.CompilerError.CompilerErrorType;
import java.util.*;

%%

%{

	private List<CompilerError> lexErrors = new ArrayList<>();
	
	public List<CompilerError> getLexErrors(){
		return lexErrors;
	}

	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\n" 	{ }
"\r"	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext()); }
"break"   	{ return new_symbol(sym.BREAK, yytext()); }
"class"   	{ return new_symbol(sym.CLASS, yytext()); }
"enum"   	{ return new_symbol(sym.ENUM, yytext()); }
"else"   	{ return new_symbol(sym.ELSE, yytext()); }
"const"		{ return new_symbol(sym.CONST, yytext()); }
"if"   		{ return new_symbol(sym.IF, yytext()); }
"switch"	{ return new_symbol(sym.SWITCH, yytext()); }
"do"   		{ return new_symbol(sym.DO, yytext()); }
"while"   	{ return new_symbol(sym.WHILE, yytext()); }
"new"   	{ return new_symbol(sym.NEW, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"read"   	{ return new_symbol(sym.READ, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"extends"   { return new_symbol(sym.EXTENDS, yytext()); }
"continue"  { return new_symbol(sym.CONTINUE, yytext()); }
"case"   	{ return new_symbol(sym.CASE, yytext()); }
"default"   { return new_symbol(sym.DEFAULT, yytext()); }
"yield"   	{ return new_symbol(sym.YIELD, yytext()); }
"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.ASTK, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%" 		{ return new_symbol(sym.MOD, yytext()); }
"==" 		{ return new_symbol(sym.EQEQ, yytext()); }
"!=" 		{ return new_symbol(sym.NEQ, yytext()); }
">" 		{ return new_symbol(sym.GT, yytext()); }
">=" 		{ return new_symbol(sym.GET, yytext()); }
"<" 		{ return new_symbol(sym.LT, yytext()); }
"<=" 		{ return new_symbol(sym.LET, yytext()); }
"&&" 		{ return new_symbol(sym.AND, yytext()); }
"||" 		{ return new_symbol(sym.OR, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
"++" 		{ return new_symbol(sym.INC, yytext()); }
"--" 		{ return new_symbol(sym.DEC, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"." 		{ return new_symbol(sym.DOT, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"[" 		{ return new_symbol(sym.LSQR, yytext()); }
"]" 		{ return new_symbol(sym.RSQR, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"?" 		{ return new_symbol(sym.QSTMK, yytext()); }
":" 		{ return new_symbol(sym.COLON, yytext()); }

<YYINITIAL> "//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

"'"[^\x00-\x1f]"'"  { return new_symbol(sym.CHAR, new Character (yytext().charAt(1))); }
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
"true"   	{ return new_symbol(sym.BOOL, yytext()); }
"false"   	{ return new_symbol(sym.BOOL, yytext()); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Lexical error ("+yytext()+") on line "+(yyline+1) + " and column " + (yycolumn+1));
	lexErrors.add(new CompilerError(yyline+1, "Lexical error unknown literal", CompilerErrorType.LEXICAL_ERROR));
}
