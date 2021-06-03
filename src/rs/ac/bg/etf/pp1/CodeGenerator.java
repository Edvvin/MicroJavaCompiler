package rs.ac.bg.etf.pp1;

import jdk.internal.org.jline.reader.SyntaxError;
import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;

	
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
	
	
}
