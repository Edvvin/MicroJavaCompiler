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
	        Tab.currentScope().addToLocals(new Obj(Obj.Type, "bool", MJStatic.boolType));

	        SemanticPass semPass = new SemanticPass();
	        prog.traverseBottomUp(semPass);
	        Tab.dump();
	        
			if(!p.errorDetected && !semPass.errorDetected) {
				File objFile = new File("test/program.obj");
				if(objFile.exists())
					objFile.delete();
				CodeGenerator codeGen = new CodeGenerator();
				Code.dataSize = semPass.nVars;
				prog.traverseBottomUp(codeGen);
				Code.mainPc = codeGen.getMainPc();
				Code.write(new FileOutputStream(objFile));
				log.error("Compilation successful!");
			}
			else {
				log.error("Compilation failed!");
			}
		} catch(IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
