package rs.ac.bg.etf.pp1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.CompilerError.CompilerErrorType;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.ac.bg.etf.pp1.ast.AstSwitchExpr;

public class SemanticAnalyzer extends VisitorAdaptor {
	

	boolean errorDetected = false;
	int printCallCount = 0;
	Obj currentMethod = null;
	Stack<Obj> currentCall = new Stack<>();
	Stack<ArrayList<AstOneActualParam>> currentCallList = new Stack<>();
	MJStatic.RelOpType currentRelOp = MJStatic.RelOpType.EQ;
	boolean returnFound = false;
	boolean hasMain = false;
	boolean insideLoop = false;
	boolean defaultFound = false;
	boolean yieldFound = false;
	int switchLevel = 0;
	int paramCnt = 0;
	int whileCnt = 0;
	Struct currType = Tab.noType;
	Struct currentYieldType = Tab.noType;
	int nVars;
	List<CompilerError> semErrors = new ArrayList<CompilerError>();
	
	public List<CompilerError> getSemErrors() {
		return semErrors;
	}

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" on line ").append(line);
		log.error(msg.toString());
		semErrors.add(new CompilerError(info.getLine(), msg.toString(), CompilerErrorType.SEMANTIC_ERROR));
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
			report_error("The program has " + nVars + "global varialbes decalred. Only 65536 allowed", program);
			return;
		}
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		if(!hasMain) {
			report_error("Funcition main not defined", program);
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
			obj = Tab.insert(Obj.Con, cnstAsgn.getCnstName(), currType);
			obj.setAdr(cnstAsgn.getValue());
			report_info("Constant declared "+ cnstAsgn.getCnstName(), cnstAsgn);
		}
	}

	public void visit(AstCnstAsgnChar cnstAsgn) {
		if(currType != Tab.charType) {
			report_error("Cannot assign char to given type", cnstAsgn);
		}
		Obj obj = Tab.find(cnstAsgn.getCnstName());
		if(obj != Tab.noObj) {
			report_error("Name " + cnstAsgn.getCnstName() + " already taken", cnstAsgn);
		}
		else {
			obj = Tab.insert(Obj.Con, cnstAsgn.getCnstName(), currType);
			obj.setAdr(Character.getNumericValue(cnstAsgn.getValue().charValue()));
			report_info("Constant declared "+ cnstAsgn.getCnstName(), cnstAsgn);
		}
	}

	public void visit(AstCnstAsgnBool cnstAsgn) {
		if(currType != MJStatic.boolType) {
			report_error("Cannot assign bool to given type", cnstAsgn);
		}
		Obj obj = Tab.find(cnstAsgn.getCnstName());
		if(obj != Tab.noObj) {
			report_error("Name " + cnstAsgn.getCnstName() + " already taken", cnstAsgn);
		}
		else {
			obj = Tab.insert(Obj.Con, cnstAsgn.getCnstName(), currType);
			if(cnstAsgn.getValue().equals("true")) {
				obj.setAdr(1);
			}
			else {
				obj.setAdr(0);
			}
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
			report_error("Type " + type.getTypeName() + " not found in symbol table", type);
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
			report_error("Function " + currentMethod.getName() + " does not have a return statement", methodDecl);
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
		returnFound = false;
		currentMethod = null;
		paramCnt = 0;
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
		returnFound = false;
		currentMethod = null;
		paramCnt = 0;
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
	
	public void visit(AstFormalParams p) {
		currentMethod.setLevel(paramCnt);
	}
	
	public void visit(AstNoFormalParams np) {
		currentMethod.setLevel(0);
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
			report_info("Array declared "+ formalParam.getParamName(), formalParam);
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
	
	public void visit(AstFuncDesig fd) {
		currentCall.push(fd.getDesignator().obj);
		fd.obj = currentCall.peek();
	}
	
	public void visit(AstFuncCallStmt callStmt) {
		if(currentCall.peek().getKind() != Obj.Meth) {
			report_error("Designator must be a function", callStmt);
		}
		currentCall.pop();
	}
	
	public void visit(AstDoPart doPart) {
		whileCnt++;
	}
	
	public void visit(AstBreakStmt breakStmt) {
		if(whileCnt == 0) {
			report_error("Must be inside a do while loop to break", breakStmt);
		}
		if(switchLevel > 0) {
			report_error("Break not allowed inside siwtch statement", breakStmt);
		}
	}

	public void visit(AstContinueStmt continueStmt) {
		if(whileCnt == 0) {
			report_error("Must be inside a do while loop to break", continueStmt);
		}
		if(switchLevel > 0) {
			report_error("Continue not allowed inside siwtch statement", continueStmt);
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
		else if(switchLevel > 0) {
			report_error("return not allowed inside siwtch statement", returnExpr);
		}
		else if(whileCnt > 0) {
			report_error("return not allowed inside while", returnExpr);
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
		else if(switchLevel > 0) {
			report_error("return not allowed inside siwtch statement", returnExpr);
		}
		else if(whileCnt > 0) {
			report_error("return not allowed inside while", returnExpr);
		}
		else {
			Struct currMethType = currentMethod.getType();
			if (currMethType != Tab.noType) {
				report_error("'return' statement without an expression must be inside a 'void' function", returnExpr);
			}			  	     	
		}
	}
	
	public void visit(AstMatchedIf ifStmt) {
	}

	public void visit(AstUnmatchedIf ifStmt) {
	}
	
	public void visit(AstDoWhile doWhileStmt) {
		whileCnt--;
	}
	
	public void visit(AstSwitchBegin switchbegin) {
		defaultFound = false;
		switchLevel++;
	}
	
	public void visit(AstSwitchExpr switchExpr) {
		if(!defaultFound) {
			report_error("The switch expression must have a default branch", switchExpr);
		}
		switchExpr.struct = currentYieldType;
		if(switchLevel == 0) {
			report_error("Cannot yield outside of switch", switchExpr);
		}
		else switchLevel--;
	}
	
	public void visit(AstCaseBegin caseBegin) {
		yieldFound = false;
	}

	public void visit(AstCase c) {
		if(!yieldFound) {
			report_error("Case branch does not have a yield statement", c);
		}
	}

	public void visit(AstDefaultBegin defualtBegin) {
		yieldFound = false;
	}

	public void visit(AstDefault d) {
		if(defaultFound) {
			report_error("Cannot have two default branches in a single switch stmt", d);
		}
		defaultFound = true;
		if(!yieldFound) {
			report_error("Default branch does not have a yield statement", d);
		}
	}
	
	public void visit(AstYield y) {
		yieldFound = true;
		if(currentYieldType == Tab.noType) {
			currentYieldType = y.getExpr().struct;
		}
		else if(y.getExpr().struct.compatibleWith(currentYieldType)) {
			report_error("All yield statement types must be compatible", y);
		}
	}
	
	public void visit(AstStartActualParams actStart) {
		currentCallList.push(new ArrayList<>());
	}
	
	public void visit(AstOneActualParam actParam) {
		currentCallList.peek().add(actParam);
	}
	
	public void visit(AstActualParams actualParams) {
		if(currentCallList.peek().size() != currentCall.peek().getLevel()) {
			report_error("The number of actual parameters does not match the number of the formal parameters", actualParams);
		}
		else {
			int i = 0;
			Collection<Obj> formalParams = currentCall.peek().getLocalSymbols();
			for(Obj o : formalParams) {
				if(i >= currentCall.peek().getLevel())
					break;
				if(!currentCallList.peek().get(i).getExpr().struct.assignableTo(o.getType())) {
					report_error("Actual parameter is not assignable to formal parameter", currentCallList.peek().get(i));
				}
				i++;
			}
		
		}
		currentCallList.pop();
	}
	
	public void visit(AstNoActualParams noact) {
		if(currentCall.peek().getLevel() > 0) {
			report_error("The number of actual parameters does not match the number of the formal parameters", noact);
		}
	}
	
	public void visit(AstNegExpr negExpr) {
		if(negExpr.getSumExpr().struct != Tab.intType) {
			report_error("To negate an expression it must be of type int", negExpr);
		}
		negExpr.struct = negExpr.getSumExpr().struct;
	}
	
	public void visit(AstPosExpr posExpr) {
		posExpr.struct = posExpr.getSumExpr().struct;
	}
	
	
	public void visit(AstCondFact condFact) {
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
		addExpr.struct = addExpr.getSumExpr().struct;
	}
	
	public void visit(AstTerExpr terExpr) {
		if(!terExpr.getExpr11().struct.equals(terExpr.getExpr12().struct)) {
			report_error("Second and third operand of the ternary '?:' operator must be of equal types", terExpr);
		}
		if(terExpr.getExpr1().struct != MJStatic.boolType) {
			report_error("Index must be of type integer", terExpr);
		}
		terExpr.struct = terExpr.getExpr11().struct;
	}
	
	public void visit(AstNotTerExpr ntExpr) {
		ntExpr.struct = ntExpr.getExpr1().struct;
	}
	
	public void visit(AstTermL term) {
		if(term.getTerm().struct != Tab.intType ||
				term.getFactor().struct != Tab.intType) {
			report_error("Only factors of type int can be use with the " + MJStatic.relOpToString(currentRelOp) + " operation", term);
		}
		term.struct = term.getTerm().struct;
	}
	
	public void visit(AstTermOne term) {
		term.struct = term.getFactor().struct;
	}
	
	public void visit(AstTermExpr expr) {
		expr.struct = expr.getTerm().struct;
	}
	
	public void visit(AstFactNum fact) {
		fact.struct = Tab.intType;
	}

	public void visit(AstFactChar fact) {
		fact.struct = Tab.charType;
	}

	public void visit(AstFactBool fact) {
		fact.struct = MJStatic.boolType;
	}

	public void visit(AstBraceExpr fact) {
		fact.struct = fact.getExpr().struct;
	}
	
	public void visit(AstDesigFact fact) {
		fact.struct = fact.getDesignator().obj.getType();
	}

	public void visit(AstFuncCallFact callFact) {
		if(currentCall.peek().getKind() != Obj.Meth) {
			report_error("Designator must be a function", callFact);
		}
		callFact.struct = currentCall.peek().getType();
		currentCall.pop();
	}
	
	public void visit(AstNewArray newArray) {
		if(newArray.getExpr().struct != Tab.intType) {
			report_error("Number of elements of array must be an integer ", newArray);
		}
		newArray.struct = new Struct(Struct.Array, newArray.getType().struct);
	}
	
	public void visit(AstDesig desig) {
		Obj obj = Tab.find(desig.getName());
		if(obj == Tab.noObj) {
			report_error("Name " + desig.getName() + " not declared", desig);
		}
		desig.obj = obj;
	}
	
	public void visit(AstIndexDesig indexDesig) {
		if(indexDesig.getDesignator().obj == null)
			return;
		if(indexDesig.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error("Designator being indexed must be an array", indexDesig);
		}
		else if(indexDesig.getExpr().struct != Tab.intType) {
			report_error("Index must be of type integer", indexDesig);
		}
		else {
			Obj obj = indexDesig.getDesignator().obj;
			indexDesig.obj = new Obj(Obj.Elem, "Elem@" + obj.getName(), obj.getType().getElemType());
			report_info("Accessing element from " + obj.getName(), indexDesig);
		}
	}
	
}

