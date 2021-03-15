// generated with ast extension for cup
// version 0.8
// 15/2/2021 22:6:37


package rs.ac.bg.etf.pp1.ast;

public class AstVarAsgnSemi extends VarAsgnList {

    private VarAsgnOne VarAsgnOne;

    public AstVarAsgnSemi (VarAsgnOne VarAsgnOne) {
        this.VarAsgnOne=VarAsgnOne;
        if(VarAsgnOne!=null) VarAsgnOne.setParent(this);
    }

    public VarAsgnOne getVarAsgnOne() {
        return VarAsgnOne;
    }

    public void setVarAsgnOne(VarAsgnOne VarAsgnOne) {
        this.VarAsgnOne=VarAsgnOne;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarAsgnOne!=null) VarAsgnOne.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarAsgnOne!=null) VarAsgnOne.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarAsgnOne!=null) VarAsgnOne.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstVarAsgnSemi(\n");

        if(VarAsgnOne!=null)
            buffer.append(VarAsgnOne.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstVarAsgnSemi]");
        return buffer.toString();
    }
}
