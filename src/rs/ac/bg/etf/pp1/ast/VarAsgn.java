// generated with ast extension for cup
// version 0.8
// 12/2/2021 0:46:30


package rs.ac.bg.etf.pp1.ast;

public class VarAsgn extends VarAsgnList {

    private VarAsgnOne VarAsgnOne;
    private VarAsgnList VarAsgnList;

    public VarAsgn (VarAsgnOne VarAsgnOne, VarAsgnList VarAsgnList) {
        this.VarAsgnOne=VarAsgnOne;
        if(VarAsgnOne!=null) VarAsgnOne.setParent(this);
        this.VarAsgnList=VarAsgnList;
        if(VarAsgnList!=null) VarAsgnList.setParent(this);
    }

    public VarAsgnOne getVarAsgnOne() {
        return VarAsgnOne;
    }

    public void setVarAsgnOne(VarAsgnOne VarAsgnOne) {
        this.VarAsgnOne=VarAsgnOne;
    }

    public VarAsgnList getVarAsgnList() {
        return VarAsgnList;
    }

    public void setVarAsgnList(VarAsgnList VarAsgnList) {
        this.VarAsgnList=VarAsgnList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarAsgnOne!=null) VarAsgnOne.accept(visitor);
        if(VarAsgnList!=null) VarAsgnList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarAsgnOne!=null) VarAsgnOne.traverseTopDown(visitor);
        if(VarAsgnList!=null) VarAsgnList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarAsgnOne!=null) VarAsgnOne.traverseBottomUp(visitor);
        if(VarAsgnList!=null) VarAsgnList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarAsgn(\n");

        if(VarAsgnOne!=null)
            buffer.append(VarAsgnOne.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarAsgnList!=null)
            buffer.append(VarAsgnList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarAsgn]");
        return buffer.toString();
    }
}
