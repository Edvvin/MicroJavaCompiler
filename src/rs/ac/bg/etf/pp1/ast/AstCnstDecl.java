// generated with ast extension for cup
// version 0.8
// 15/5/2021 0:40:51


package rs.ac.bg.etf.pp1.ast;

public class AstCnstDecl extends CnstDecl {

    private Type Type;
    private CnstAsgnList CnstAsgnList;

    public AstCnstDecl (Type Type, CnstAsgnList CnstAsgnList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.CnstAsgnList=CnstAsgnList;
        if(CnstAsgnList!=null) CnstAsgnList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(Type!=null) Type.accept(visitor);
        if(CnstAsgnList!=null) CnstAsgnList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(CnstAsgnList!=null) CnstAsgnList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(CnstAsgnList!=null) CnstAsgnList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstCnstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CnstAsgnList!=null)
            buffer.append(CnstAsgnList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstCnstDecl]");
        return buffer.toString();
    }
}
