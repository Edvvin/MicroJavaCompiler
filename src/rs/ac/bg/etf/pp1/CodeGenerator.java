package rs.ac.bg.etf.pp1;

import java.util.*;

import jdk.internal.org.jline.reader.SyntaxError;
import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	private Stack<Integer> ops = new Stack<>();
	private Stack<Integer> lastPatch = new Stack<>();
	private Stack<ArrayList<Integer>> yields = new Stack<>();
	private Stack<Integer> defBranches = new Stack<>();

	
	public int getMainPc() {
		return mainPc;
	}
	
	public void visit(AstProgName pName) {

		// ORD
		Tab.ordObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);	
		
		// CHR
		Tab.chrObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.loadConst(256);
		Code.put(Code.rem);
		Code.put(Code.exit);
		Code.put(Code.return_);	

		// LEN
		Tab.lenObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);	
	}
	
	public void visit(AstMethTypeName methName) {
		//if(methName.getMethName().equalsIgnoreCase("main")) {
			//mainPc = Code.pc; main must be void
		//}
		
		methName.obj.setAdr(Code.pc);
		SyntaxNode methNode = methName.getParent();
		
		VarCounter varCnt = new VarCounter();
		methNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methNode.traverseTopDown(fpCnt);
		
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());

	}

	public void visit(AstMethVoidName methName) {
		if(methName.getMethName().equalsIgnoreCase("main")) {
			mainPc = Code.pc;
		}
		
		methName.obj.setAdr(Code.pc);
		SyntaxNode methNode = methName.getParent();
		
		VarCounter varCnt = new VarCounter();
		methNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methNode.traverseTopDown(fpCnt);
		
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());

	}

	public void visit(AstMethDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(AstFuncCallFact funcCall){
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit(AstFuncCallStmt funcCall){
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if(funcCall.getDesignator().obj.getType() != Tab.noType){
			Code.put(Code.pop);
		}
	}
	
	public void visit(AstReturnExpr returnExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(AstReturnNoExpr returnExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(AstDesig designator){
		SyntaxNode parent = designator.getParent();
		
		if(AstFuncCallFact.class != parent.getClass() 
			&& AstFuncCallStmt.class != parent.getClass() 
			&& AstReadStmt.class != parent.getClass() 
			&& AstEqualStmt.class != parent.getClass()
			)
		{
			Code.load(designator.obj);
		}
	}
	
	public void visit(AstEqualStmt eqStmt) {
		Code.store(eqStmt.getDesignator().obj);
	}
	
	public void visit(AstIncStmt incStmt) {
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(incStmt.getDesignator().obj);
		Code.put(Code.pop);
	}
	
	public void visit(AstNewArray newArr) {
		Code.put(Code.newarray);
		if(newArr.getType().struct.getKind() == Struct.Char) {
			Code.put(0);
		}
		else {
			Code.put(1);
		}
	}

	public void visit(AstDecStmt decStmt) {
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(decStmt.getDesignator().obj);
		Code.put(Code.pop);
	}
	
	public void visit(AstFactNum num) {
		Code.loadConst(num.getN1());
	}
	
	public void visit(AstFactChar c) {
		Code.loadConst(Character.getNumericValue(c.getC1()));
	}
	
	public void visit(AstFactBool b) {
		if(b.getB1().equals("true")) {
			Code.put(Code.const_1);
		}
		else {
			Code.put(Code.const_n);
		}
	}
	
	public void visit(AstTermL term) {
		Code.put(ops.pop());
	}
	
	public void visit(AstSwitchBegin sb) {
		lastPatch.push(0);
		defBranches.push(0);
		yields.push(new ArrayList<>());
	}
	
	public void visit(AstSwitchExpr se) {
		int temp = lastPatch.pop();
		if(temp == 0)
			Code.fixup(temp);
		Code.putJump(defBranches.pop());
		ArrayList<Integer> yieldFixes = yields.pop();
		for (Integer y : yieldFixes) {
			Code.fixup(y);
		}
	}
	
	public void visit(AstCaseBegin c) {
		int temp = lastPatch.pop();
		if(temp != 0)
			Code.fixup(temp);
		Code.put(Code.dup);
		Code.loadConst(c.getNumConst());
		Code.putFalseJump(Code.eq, 0);
		lastPatch.push(Code.pc-2);
	}

	public void visit(AstCase c) {
	}
	
	public void visit(AstDefaultBegin db) {
		defBranches.pop();
		defBranches.push(Code.pc);
	}
	
	public void visit(AstDefault d) {
		
	}
	
	public void visit(AstYieldBegin yb) {
		Code.put(Code.pop); // To remove the expression from the switch
	}
	
	public void visit(AstYield y) {
		Code.putJump(0); //switchEndAdr
		yields.firstElement().add(Code.pc-2);
	}
	
	
	public void visit(AstNegExpr ne) {
		Code.put(Code.neg);
	}
	
	public void visit(AstAddExpr ae) {
		Code.put(ops.pop());
	}
	
	public void visit(AstMulop mul) {
		ops.push(Code.mul);
	}

	public void visit(AstDivop div) {
		ops.push(Code.div);
	}

	public void visit(AstModop mod) {
		ops.push(Code.rem);
	}

	public void visit(AstAddop add) {
		ops.push(Code.add);
	}

	public void visit(AstSubop sub) {
		ops.push(Code.sub);
	}
	
	
}
