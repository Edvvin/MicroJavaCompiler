// generated with ast extension for cup
// version 0.8
// 10/5/2021 18:33:52


package rs.ac.bg.etf.pp1.ast;

public class AstCnstAsgnOne extends CnstAsgnList {

    private CnstAsgn CnstAsgn;

    public AstCnstAsgnOne (CnstAsgn CnstAsgn) {
        this.CnstAsgn=CnstAsgn;
        if(CnstAsgn!=null) CnstAsgn.setParent(this);
    }

    public CnstAsgn getCnstAsgn() {
        return CnstAsgn;
    }

    public void setCnstAsgn(CnstAsgn CnstAsgn) {
        this.CnstAsgn=CnstAsgn;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CnstAsgn!=null) CnstAsgn.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CnstAsgn!=null) CnstAsgn.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CnstAsgn!=null) CnstAsgn.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstCnstAsgnOne(\n");

        if(CnstAsgn!=null)
            buffer.append(CnstAsgn.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstCnstAsgnOne]");
        return buffer.toString();
    }
}
