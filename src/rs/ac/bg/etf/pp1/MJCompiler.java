package rs.ac.bg.etf.pp1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.util.*;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class MJCompiler implements Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	private Logger log;

	
	public MJCompiler() {
		
	}

	@Override
	public List<CompilerError> compile(String sourceFilePath, String outputFilePath) {
		log = Logger.getLogger(MJCompiler.class);

		File src = new File(sourceFilePath);
		List<CompilerError> errors = new ArrayList<CompilerError>();

		if (!src.exists()) {
			log.error("Source file [" + src.getAbsolutePath() + "] not found!");
			return null;
		}

		log.info("Compiling source file: " + src.getAbsolutePath());
		
		try (BufferedReader br = new BufferedReader(new FileReader(src))) {
			Yylex lexer = new Yylex(br);
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();
	        errors.addAll(lexer.getLexErrors());
	        errors.addAll(p.getSynErrors());
	        SyntaxNode prog = (SyntaxNode)(s.value);
	        log.info(prog.toString());
	        Tab.init();
	        Tab.currentScope().addToLocals(new Obj(Obj.Type, "bool", MJStatic.boolType));

	        SemanticAnalyzer semPass = new SemanticAnalyzer();
	        prog.traverseBottomUp(semPass);
	        Tab.dump();
	        errors.addAll(semPass.getSemErrors());
	        
			if(!p.errorDetected && !semPass.errorDetected) {
				File objFile = new File(outputFilePath);
				if(objFile.exists())
					objFile.delete();
				CodeGenerator codeGen = new CodeGenerator();
				Code.dataSize = semPass.nVars;
				prog.traverseBottomUp(codeGen);
				Code.mainPc = codeGen.getMainPc();
				Code.write(new FileOutputStream(objFile));
				log.info("Compilation successful!");
			}
			else {
				log.info("Compilation failed!");
			}
			if(errors.size()>0) {
				return errors;
			}
			else {
				return null;
			}
		} catch(IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
