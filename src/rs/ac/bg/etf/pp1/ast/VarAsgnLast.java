// generated with ast extension for cup
// version 0.8
// 12/2/2021 0:46:30


package rs.ac.bg.etf.pp1.ast;

public class VarAsgnLast extends VarAsgnList {

    private VarAsgnOne VarAsgnOne;

    public VarAsgnLast (VarAsgnOne VarAsgnOne) {
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
        buffer.append("VarAsgnLast(\n");

        if(VarAsgnOne!=null)
            buffer.append(VarAsgnOne.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarAsgnLast]");
        return buffer.toString();
    }
}
