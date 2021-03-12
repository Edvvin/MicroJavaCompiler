// generated with ast extension for cup
// version 0.8
// 12/2/2021 17:45:15


package rs.ac.bg.etf.pp1.ast;

public class AstCnstAsgn extends CnstAsgnList {

    private String I1;
    private CnstValue CnstValue;
    private CnstAsgnList CnstAsgnList;

    public AstCnstAsgn (String I1, CnstValue CnstValue, CnstAsgnList CnstAsgnList) {
        this.I1=I1;
        this.CnstValue=CnstValue;
        if(CnstValue!=null) CnstValue.setParent(this);
        this.CnstAsgnList=CnstAsgnList;
        if(CnstAsgnList!=null) CnstAsgnList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public CnstValue getCnstValue() {
        return CnstValue;
    }

    public void setCnstValue(CnstValue CnstValue) {
        this.CnstValue=CnstValue;
    }

    public CnstAsgnList getCnstAsgnList() {
        return CnstAsgnList;
    }

    public void setCnstAsgnList(CnstAsgnList CnstAsgnList) {
        this.CnstAsgnList=CnstAsgnList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CnstValue!=null) CnstValue.accept(visitor);
        if(CnstAsgnList!=null) CnstAsgnList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CnstValue!=null) CnstValue.traverseTopDown(visitor);
        if(CnstAsgnList!=null) CnstAsgnList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CnstValue!=null) CnstValue.traverseBottomUp(visitor);
        if(CnstAsgnList!=null) CnstAsgnList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstCnstAsgn(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(CnstValue!=null)
            buffer.append(CnstValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CnstAsgnList!=null)
            buffer.append(CnstAsgnList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstCnstAsgn]");
        return buffer.toString();
    }
}
