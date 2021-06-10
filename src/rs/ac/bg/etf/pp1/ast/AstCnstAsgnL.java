// generated with ast extension for cup
// version 0.8
// 11/5/2021 0:35:3


package rs.ac.bg.etf.pp1.ast;

public class AstCnstAsgnL extends CnstAsgnList {

    private CnstAsgn CnstAsgn;
    private CnstAsgnList CnstAsgnList;

    public AstCnstAsgnL (CnstAsgn CnstAsgn, CnstAsgnList CnstAsgnList) {
        this.CnstAsgn=CnstAsgn;
        if(CnstAsgn!=null) CnstAsgn.setParent(this);
        this.CnstAsgnList=CnstAsgnList;
        if(CnstAsgnList!=null) CnstAsgnList.setParent(this);
    }

    public CnstAsgn getCnstAsgn() {
        return CnstAsgn;
    }

    public void setCnstAsgn(CnstAsgn CnstAsgn) {
        this.CnstAsgn=CnstAsgn;
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
        if(CnstAsgn!=null) CnstAsgn.accept(visitor);
        if(CnstAsgnList!=null) CnstAsgnList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CnstAsgn!=null) CnstAsgn.traverseTopDown(visitor);
        if(CnstAsgnList!=null) CnstAsgnList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CnstAsgn!=null) CnstAsgn.traverseBottomUp(visitor);
        if(CnstAsgnList!=null) CnstAsgnList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstCnstAsgnL(\n");

        if(CnstAsgn!=null)
            buffer.append(CnstAsgn.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CnstAsgnList!=null)
            buffer.append(CnstAsgnList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstCnstAsgnL]");
        return buffer.toString();
    }
}
