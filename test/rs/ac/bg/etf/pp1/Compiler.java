package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class Compiler {
	
	public static final int Bool = 5;
	public static final Struct boolType = new Struct(Compiler.Bool);

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) {
		Logger log = Logger.getLogger(Compiler.class);
		File src;
		
		if(args.length >= 1) {
			src = new File(args[0]);
		}
		else {
			src = new File("test/program.mj");
		}
		
		if (!src.exists()) {
			log.error("Source file [" + src.getAbsolutePath() + "] not found!");
			return;
		}
			
		log.info("Compiling source file: " + src.getAbsolutePath());
		
		try (BufferedReader br = new BufferedReader(new FileReader(src))) {
			Yylex lexer = new Yylex(br);
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        SyntaxNode prog = (SyntaxNode)(s.value);
	        log.info(prog.toString());
	        Tab.init();
	        Tab.currentScope().addToLocals(new Obj(Obj.Type, "bool", Compiler.boolType));

	        SemanticPass semPass = new SemanticPass();
	        prog.traverseBottomUp(semPass);
	        Tab.dump();
		} catch(IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
