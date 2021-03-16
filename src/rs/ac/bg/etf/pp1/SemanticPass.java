package rs.ac.bg.etf.pp1;
import java.util.ArrayList;

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
	Obj currentCall = null;
	ArrayList<Struct> currentCallList = new ArrayList<Struct>();
	MJStatic.RelOpType currentRelOp = MJStatic.RelOpType.EQ;
	boolean returnFound = false;
	boolean hasMain = false;
	boolean insideLoop = false;
	int paramCnt = 0;
	int whileCnt = 0;
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
		if(currType != MJStatic.boolType) {
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
		if(eqStmt.getDesignator().obj.getKind() != Obj.Var 
				&& eqStmt.getDesignator().obj.getKind() != Obj.Elem ) {
			report_error("Designator may only be either a variable or array element", eqStmt);
		}
		if (!eqStmt.getExpr().struct.assignableTo(eqStmt.getDesignator().obj.getType())) {
			report_error("Cannot assign. Incompatible types", eqStmt);
		}
	}

	public void visit(AstIncStmt eqStmt) {
		if(eqStmt.getDesignator().obj.getKind() != Obj.Var 
				&& eqStmt.getDesignator().obj.getKind() != Obj.Elem ) {
			report_error("Designator may only be either a variable or array element", eqStmt);
		}
		if (eqStmt.getDesignator().obj.getType() != Tab.intType) {
			report_error("Only designators of type int can be incremented", eqStmt);
		}
	}

	public void visit(AstDecStmt eqStmt) {
		if(eqStmt.getDesignator().obj.getKind() != Obj.Var 
				&& eqStmt.getDesignator().obj.getKind() != Obj.Elem ) {
			report_error("Designator may only be either a variable or array element", eqStmt);
		}
		if (eqStmt.getDesignator().obj.getType() != Tab.intType) {
			report_error("Only designators of type int can be decremented", eqStmt);
		}
	}
	
	public void visit(AstFuncCallStmt callStmt) {
		currentCall = callStmt.getDesignator().obj;
		if(currentCall.getKind() != Obj.Meth) {
			report_error("Designator must be a function", callStmt);
		}
	}
	
	public void visit(AstDoPart doPart) {
		whileCnt++;
	}
	
	public void visit(AstBreakStmt breakStmt) {
		if(whileCnt == 0) {
			report_error("Must be inside a do while loop to break", breakStmt);
		}
	}

	public void visit(AstContinueStmt continueStmt) {
		if(whileCnt == 0) {
			report_error("Must be inside a do while loop to break", continueStmt);
		}
	}
	
	public void visit(AstReadStmt readStmt) {
		if(readStmt.getDesignator().obj.getKind() != Obj.Var 
				&& readStmt.getDesignator().obj.getKind() != Obj.Elem ) {
			report_error("Designator may only be either a variable or array element", readStmt);
		}
		if (readStmt.getDesignator().obj.getType() != Tab.intType &&
				readStmt.getDesignator().obj.getType() != Tab.charType &&
				readStmt.getDesignator().obj.getType() != MJStatic.boolType ) {
			report_error("Designator must be either of type int, char of bool", readStmt);
		}
	}

	public void visit(AstPrintStmtParam printStmt){
		if (printStmt.getExpr().struct != Tab.intType &&
				printStmt.getExpr().struct != Tab.charType &&
				printStmt.getExpr().struct != MJStatic.boolType ) {
			report_error("Expression must be either of type int, char of bool", printStmt);
		}
	}

	public void visit(AstPrintStmt printStmt){
		if (printStmt.getExpr().struct != Tab.intType &&
				printStmt.getExpr().struct != Tab.charType &&
				printStmt.getExpr().struct != MJStatic.boolType ) {
			report_error("Expression must be either of type int, char of bool", printStmt);
		}
	}
	
	public void visit(AstReturnExpr returnExpr){
		returnFound = true;
		if(currentMethod == null) {
			report_error("'return' cannot be found outside a function", returnExpr);
		}
		else {
			Struct currMethType = currentMethod.getType();
			if (!currMethType.equals(returnExpr.getExpr().struct)) {
				report_error("Cannot return given type. Incompatible with method return type", returnExpr);
			}			  	     	
		}
	}

	public void visit(AstReturnNoExpr returnExpr){
		returnFound = true;
		if(currentMethod == null) {
			report_error("'return' cannot be found outside a function", returnExpr);
		}
		else {
			Struct currMethType = currentMethod.getType();
			if (currMethType != Tab.noType) {
				report_error("'return' statement without an expression must be inside a 'void' function", returnExpr);
			}			  	     	
		}
	}
	
	public void visit(AstMatchedIf ifStmt) {
		if(ifStmt.getCondition().struct != MJStatic.boolType) {
			report_error("Condition must be of type bool", ifStmt);
		}
	}

	public void visit(AstUnmatchedIf ifStmt) {
		if(ifStmt.getCondition().struct != MJStatic.boolType) {
			report_error("Condition must be of type bool", ifStmt);
		}
	}
	
	public void visit(AstDoWhile doWhileStmt) {
		whileCnt--;
		if(doWhileStmt.getCondition().struct != MJStatic.boolType) {
			report_error("Condition must be of type bool", doWhileStmt);
		}
	}
	
	public void visit(AstStartActualParams actStart) {
		currentCallList = new ArrayList<>();
	}
	
	public void visit(AstActualParamsL actParam) {
		currentCallList.add(actParam.getExpr().struct);
	}
	
	public void visit(AstActualParamsOne actParam) {
		currentCallList.add(actParam.getExpr().struct);
	}
	
	public void visit(AstActualParams actualParams) {
		if(currentCallList.size() != currentCall.getLevel()) {
			report_error("The number of actual parameters does not match the number of the formal parameters", actualParams);
		}
		else {
			//TODO At the end so you can test it
		}
	}
	
	public void visit(AstNoActualParam actualParams) {
		if(currentCallList.size() > 0) {
			report_error("The function does not accept parameters ", actualParams);
		}
	}
	
	public void visit(AstNegExpr negExpr) {
		if(negExpr.getSumExpr().struct != Tab.intType) {
			report_error("To negate an expression it must be of type int", negExpr);
		}
	}
	
	
	public void visit(AstCondFactL condFact) {
		if(!condFact.getExpr().struct.compatibleWith(condFact.getExpr1().struct)) {
			report_error("Cannot compare incompatible types", condFact);
		}
		else {
			if(condFact.getExpr().struct.getKind() == Struct.Array) {
				if(currentRelOp != MJStatic.RelOpType.EQ && currentRelOp != MJStatic.RelOpType.NE) {
					report_error("Reference types may only be compared using == or !=", condFact);
				}
			}
		}
	}
	
	public void visit(AstEqop relop) {
		currentRelOp = MJStatic.RelOpType.EQ;
	}

	public void visit(AstNeop relop) {
		currentRelOp = MJStatic.RelOpType.NE;
	}

	public void visit(AstGtop relop) {
		currentRelOp = MJStatic.RelOpType.G;
	}
	
	public void visit(AstGetop relop) {
		currentRelOp = MJStatic.RelOpType.GE;
	}
	
	public void visit(AstLtop relop) {
		currentRelOp = MJStatic.RelOpType.L;
	}
	
	public void visit(AstLetop relop) {
		currentRelOp = MJStatic.RelOpType.LE;
	}
	
	public void visit(AstAddExpr addExpr) {
		if(addExpr.getSumExpr().struct != Tab.intType ||
				addExpr.getTerm().struct != Tab.intType) {
			report_error("Only terms of type int can be use with the " + MJStatic.relOpToString(currentRelOp) + " operation", addExpr);
		}
	}
	
	public void visit(AstTerExpr terExpr) {
		if(!terExpr.getExpr11().struct.equals(terExpr.getExpr12().struct)) {
			report_error("Second and third operand of the ternary '?:' operator must be of equal types", terExpr);
		}
		if(terExpr.getExpr1().struct != MJStatic.boolType) {
			report_error("The first operand of the ternary '?:' operator must be bool", terExpr);
		}
	}
	
	public void visit(AstTermL term) {
		if(term.getTerm().struct != Tab.intType ||
				term.getFactor().struct != Tab.intType) {
			report_error("Only factors of type int can be use with the " + MJStatic.relOpToString(currentRelOp) + " operation", term);
		}
	}

	public void visit(AstFuncCallFact callFact) {
		currentCall = callFact.getDesignator().obj;
		if(currentCall.getKind() != Obj.Meth) {
			report_error("Designator must be a function", callFact);
		}
	}
	
	public void visit(AstNewArray newArray) {
		if(newArray.getExpr().struct != Tab.intType) {
			report_error("Number of elements of array must be an integer ", newArray);
		}
	}
	
	public void visit(AstIndexDesig indexDesig) {
		if(indexDesig.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error("Designator being indexed must be an array", indexDesig);
		}
		if(indexDesig.getExpr().struct != Tab.intType) {
			report_error("Index must be of type integer", indexDesig);
		}
	}
	
}

