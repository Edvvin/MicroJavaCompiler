package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.AstFormalParamArrDecl;
import rs.ac.bg.etf.pp1.ast.AstFormalParamDecl;
import rs.ac.bg.etf.pp1.ast.AstVarArrAsgn;
import rs.ac.bg.etf.pp1.ast.AstVarAsgn;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
	
	public int getCount(){
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
	
		public void visit(AstFormalParamDecl formParamDecl){
			count++;
		}

		public void visit(AstFormalParamArrDecl formParamDecl){
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor{
		
		public void visit(AstVarAsgn varDecl){
			count++;
		}

		public void visit(AstVarArrAsgn varDecl){
			count++;
		}
	}
}
