package rs.ac.bg.etf.pp1;

import java.util.*;

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
	private Stack<Integer> ifExit = new Stack<>();
	private Stack<Integer> elseExit = new Stack<>();
	private Stack<Integer> whileExit = new Stack<>();
	private Stack<Integer> whileDo = new Stack<>();
	private Stack<ArrayList<Integer>> breaks = new Stack<>();
	private Stack<Integer> ter1Exit = new Stack<>();
	private Stack<Integer> ter2Exit = new Stack<>();

	
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
	
	private void printBool(int w) {
			int temp = 0;
			Code.loadConst(0);
			Code.putFalseJump(Code.eq, 0);
			temp = Code.pc-2;
			Code.loadConst('F');
			Code.loadConst(w);
			Code.putJump(0);
			Code.fixup(temp);
			temp = Code.pc-2;
			Code.loadConst('T');
			Code.loadConst(w);
			Code.fixup(temp);
			Code.put(Code.bprint);
	}
	
	public void visit(AstPrintStmt ps) {
		if(ps.getExpr().struct == Tab.intType) {
			Code.loadConst(5);
			Code.put(Code.print);
		}
		else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(AstPrintStmtParam ps) {
		if(ps.getExpr().struct == Tab.intType) {
			Code.loadConst(ps.getN2());
			Code.put(Code.print);
		}
		else {
			Code.loadConst(ps.getN2());
			Code.put(Code.bprint);
		}
	}
	
	public void visit(AstReadStmt rs) {
		if(rs.getDesignator().obj.getType() == Tab.intType) {
			Code.put(Code.read);
		}
		else {
			Code.put(Code.bread);
		}
		Code.store(rs.getDesignator().obj);
	}
	
	public void visit(AstDoPart dp) {
		whileDo.push(Code.pc);
		breaks.push(new ArrayList<>());
	}
	
	public void visit(AstDoWhile dw) {
		Code.loadConst(0);
		Code.putFalseJump(Code.inverse[Code.gt], whileDo.pop());
		for(Integer br : breaks.peek()) {
			Code.fixup(br);
		}
		breaks.pop();
	}
	
	public void visit(AstBreakStmt bstmt) {
		Code.putJump(0);
		breaks.peek().add(Code.pc - 2);
	}
	
	public void visit(AstContinueStmt cont) {
		Code.putJump(whileDo.peek());
	}
	
	public void visit(AstTerExpr ter) {
		Code.fixup(ter2Exit.pop());
	}
	
	public void visit(AstTerQstmk qmk) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		ter1Exit.push(Code.pc - 2);
	}

	public void visit(AstTerColon col) {
		Code.putJump(0);
		ter2Exit.push(Code.pc - 2);
		Code.fixup(ter1Exit.pop());
	}
	
	public void visit(AstUnmatchedIf uif) {
		Code.fixup(ifExit.pop());
	}
	
	public void visit(AstMatchedIf mif) {
		Code.fixup(elseExit.pop());
	}
	
	public void visit(AstIfPart ifp) {
		Code.loadConst(0);
		Code.putFalseJump(Code.gt, 0);
		ifExit.push(Code.pc-2);
	}

	public void visit(AstElsePart ep) {
		Code.putJump(0);
		elseExit.push(Code.pc-2);
		Code.fixup(ifExit.pop());
	}
	
	public void visit(AstFuncCallFact funcCall){
		Obj functionObj = funcCall.getFuncDesig().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit(AstFuncCallStmt funcCall){
		Obj functionObj = funcCall.getFuncDesig().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if(funcCall.getFuncDesig().obj.getType() != Tab.noType){
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
		SyntaxNode p = designator.getParent();
		
		if(AstFuncCallFact.class != p.getParent().getClass()
			&& AstFuncCallStmt.class != p.getParent().getClass() 
			&& AstReadStmt.class != p.getClass() 
			&& AstEqualStmt.class != p.getClass()
			)
		{
			Code.load(designator.obj);
		}
	}
	
	public void visit(AstIndexDesig indDesig) {
		SyntaxNode p = indDesig.getParent();

		if(AstReadStmt.class != p.getClass() 
			&& AstEqualStmt.class != p.getClass()
			)
		{
			Code.put(Code.dup2);
			Code.load(indDesig.obj);
		}
	}
	
	public void visit(AstEqualStmt eqStmt) {
		Code.store(eqStmt.getDesignator().obj);
	}
	
	public void visit(AstIncStmt incStmt) {
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(incStmt.getDesignator().obj);
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
	}
	
	public void visit(AstConditionL condList) {
		Code.put(Code.add);
	}
	
	public void visit(AstCondTermL termList) {
		Code.put(Code.mul);
	}
	
	public void visit(AstCondFact cf) {
		Code.putFalseJump(ops.pop(), 0);
		int tempPC1 = Code.pc-2;
		Code.loadConst(1);
		Code.putJump(0);
		int tempPC2 = Code.pc-2;
		Code.fixup(tempPC1);
		Code.loadConst(0);
		Code.fixup(tempPC2);
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
	
	public void visit(AstEqop eq) {
		ops.push(Code.eq);
	}

	public void visit(AstNeop ne) {
		ops.push(Code.ne);
	}
	
	public void visit(AstGtop gtop) {
		ops.push(Code.gt);
	}

	public void visit(AstGetop getop) {
		ops.push(Code.ge);
	}
	
	public void visit(AstLtop ltop) {
		ops.push(Code.lt);
	}

	public void visit(AstLetop letop) {
		ops.push(Code.le);
	}
	
}
