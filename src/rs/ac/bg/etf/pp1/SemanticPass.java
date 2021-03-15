package rs.ac.bg.etf.pp1;
import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {

	boolean errorDetected = false;
	int printCallCount = 0;
	Obj currentMethod = null;
	boolean returnFound = false;
	boolean hasMain = false;
	boolean insideLoop = false;
	int paramCnt = 0;
	Struct currType = Tab.noType;
	int nVars;

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" on line ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" on line ").append(line);
		log.info(msg.toString());
	}
	
	public void visit(AstProgram program) {		
		nVars = Tab.currentScope.getnVars();
		if(nVars > 65536) {
			report_error("The program has " + nVars + "global varialbes decalred. Only 65536 allowed", null);
			return;
		}
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		if(!hasMain) {
			report_error("Funcition main not defined", null);
		}
	}

	public void visit(AstProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProg(), Tab.noType);
		Tab.openScope();     	
	}
	
	public void visit(AstCnstAsgnInt cnstAsgn) {
		if(currType != Tab.intType) {
			report_error("Cannot assign int to given type", cnstAsgn);
		}
		Obj obj = Tab.find(cnstAsgn.getCnstName());
		if(obj != Tab.noObj) {
			report_error("Name " + cnstAsgn.getCnstName() + " already taken", cnstAsgn);
		}
		else {
			obj.setAdr(cnstAsgn.getValue());
			Tab.insert(Obj.Con, cnstAsgn.getCnstName(), currType);
			report_info("Constant declared "+ cnstAsgn.getCnstName(), cnstAsgn);
		}
	}

	public void visit(AstCnstAsgnChar cnstAsgn) {
		if(currType != Tab.charType) {
			report_error("Cannot assign int to given type", cnstAsgn);
		}
		Obj obj = Tab.find(cnstAsgn.getCnstName());
		if(obj != Tab.noObj) {
			report_error("Name " + cnstAsgn.getCnstName() + " already taken", cnstAsgn);
		}
		else {
			obj.setAdr(Character.getNumericValue(cnstAsgn.getValue().charValue()));
			Tab.insert(Obj.Con, cnstAsgn.getCnstName(), currType);
			report_info("Constant declared "+ cnstAsgn.getCnstName(), cnstAsgn);
		}
	}

	public void visit(AstCnstAsgnBool cnstAsgn) {
		if(currType != BoolType.boolType) {
			report_error("Cannot assign int to given type", cnstAsgn);
		}
		Obj obj = Tab.find(cnstAsgn.getCnstName());
		if(obj != Tab.noObj) {
			report_error("Name " + cnstAsgn.getCnstName() + " already taken", cnstAsgn);
		}
		else {
			if(cnstAsgn.getValue().equals("true")) {
				obj.setAdr(1);
			}
			else {
				obj.setAdr(0);
			}
			Tab.insert(Obj.Con, cnstAsgn.getCnstName(), currType);
			report_info("Constant declared "+ cnstAsgn.getCnstName(), cnstAsgn);
		}
	}

	public void visit(AstVarAsgn varAsgn) {
		Obj obj = Tab.find(varAsgn.getVarName());
		if(obj != Tab.noObj) {
			report_error("Name " + varAsgn.getVarName() + " already taken", varAsgn);
		}
		else {
			Tab.insert(Obj.Var, varAsgn.getVarName(), currType);
			report_info("Variable declared "+ varAsgn.getVarName(), varAsgn);
		}
		
	}
	
	public void visit(AstVarArrAsgn varArrAsgn) {
		Obj obj = Tab.find(varArrAsgn.getVarName());
		if(obj != Tab.noObj) {
			report_error("Name " + varArrAsgn.getVarName() + " already taken", varArrAsgn);
		}
		else {
			Tab.insert(Obj.Var, varArrAsgn.getVarName(), new Struct(Struct.Array, currType));
			report_info("Variable Array Declared "+ varArrAsgn.getVarName(), varArrAsgn);
		}
	}

	public void visit(AstType type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Type " + type.getTypeName() + " not found in symbol table", null);
			type.struct = Tab.noType;
		} 
		else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
				currType = typeNode.getType();
			} 
			else {
				report_error("Name " + type.getTypeName() + " does not represent a type", type);
				type.struct = Tab.noType;
			}
		}  
	}

	public void visit(AstMethDecl methodDecl) {
		Tab.chainLocalSymbols(currentMethod);
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Function " + currentMethod.getName() + " does not have a return statement", null);
		}
		
		if(currentMethod.getName().equals("main")) {
			hasMain = true;
			if(currentMethod.getType() != Tab.noType || paramCnt != 0) {
				report_error("Function main can only have return type 'void' and must not have formal parameters", methodDecl);
			}
		}
		
		if(Tab.currentScope.getnVars() > 256) {
				report_error("Function " + currentMethod.getName() + " has more that 256 variables", methodDecl);
		}
		
		currentMethod.setLevel(paramCnt);
		
		Tab.closeScope();
		
		returnFound = false;
		currentMethod = null;
		paramCnt = 0;
	}

	public void visit(AstMethTypeName methodTypeName) {
		Obj obj = Tab.find(methodTypeName.getMethName());
		if(obj != Tab.noObj) {
			report_error("Name " + methodTypeName.getMethName() + " already taken", methodTypeName);
		}
		else {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), currType);
			methodTypeName.obj = currentMethod;
			Tab.openScope();
			report_info("Function " + methodTypeName.getMethName() + " is being defined", methodTypeName);
		}
	}

	public void visit(AstMethVoidName methodTypeName) {
		Obj obj = Tab.find(methodTypeName.getMethName());
		if(obj != Tab.noObj) {
			report_error("Name " + methodTypeName.getMethName() + " already taken", methodTypeName);
		}
		else {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), Tab.noType);
			methodTypeName.obj = currentMethod;
			Tab.openScope();
			report_info("Function " + methodTypeName.getMethName() + "is being defined", methodTypeName);
		}
	}
	
	public void visit(AstFormalParamDecl formalParam) {
		Obj obj = Tab.find(formalParam.getParamName());
		if(obj != Tab.noObj) {
			report_error("Name " + formalParam.getParamName() + " already taken", formalParam);
		}
		else {
			Tab.insert(Obj.Var, formalParam.getParamName(), currType);
			paramCnt++;
			report_info("Variable declared "+ formalParam.getParamName(), formalParam);
		}
	}

	public void visit(AstFormalParamArrDecl formalParam) {
		Obj obj = Tab.find(formalParam.getParamName());
		if(obj != Tab.noObj) {
			report_error("Name " + formalParam.getParamName() + " already taken", formalParam);
		}
		else {
			Tab.insert(Obj.Var, formalParam.getParamName(), new Struct(Struct.Array, currType));
			paramCnt++;
			report_info("Variable declared "+ formalParam.getParamName(), formalParam);
		}
	}


	public void visit(AstEqualStmt eqStmt) {
		//if (!eqStmt.getExpr().struct.assignableTo(eqStmt.getDesignator().obj.getType())) {
		//	report_error("Cannot assign. Incompatible types", eqStmt);
		//}
	}

	public void visit(AstPrintStmt printStmt){
		printCallCount++;    	
	}

	public void visit(AstReturnExpr returnExpr){
		returnFound = true;
		Struct currMethType = currentMethod.getType();
		//if (!currMethType.compatibleWith(returnExpr.getExpr().struct)) {
		//	report_error("Cannot return given type. Incompatible with method return type", returnExpr);
		//}			  	     	
	}

	public boolean passed() {
		return !errorDetected;
	}
	
}

