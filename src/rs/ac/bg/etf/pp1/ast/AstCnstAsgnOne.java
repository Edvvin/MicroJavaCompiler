// generated with ast extension for cup
// version 0.8
// 12/2/2021 20:48:22


package rs.ac.bg.etf.pp1.ast;

public class AstCnstAsgnOne extends CnstAsgnList {

    private String I1;
    private CnstValue CnstValue;

    public AstCnstAsgnOne (String I1, CnstValue CnstValue) {
        this.I1=I1;
        this.CnstValue=CnstValue;
        if(CnstValue!=null) CnstValue.setParent(this);
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CnstValue!=null) CnstValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CnstValue!=null) CnstValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CnstValue!=null) CnstValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstCnstAsgnOne(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(CnstValue!=null)
            buffer.append(CnstValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstCnstAsgnOne]");
        return buffer.toString();
    }
}
