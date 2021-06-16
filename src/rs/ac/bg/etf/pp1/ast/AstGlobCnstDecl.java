// generated with ast extension for cup
// version 0.8
// 16/5/2021 15:11:18


package rs.ac.bg.etf.pp1.ast;

public class AstGlobCnstDecl extends Decl {

    private CnstDecl CnstDecl;

    public AstGlobCnstDecl (CnstDecl CnstDecl) {
        this.CnstDecl=CnstDecl;
        if(CnstDecl!=null) CnstDecl.setParent(this);
    }

    public CnstDecl getCnstDecl() {
        return CnstDecl;
    }

    public void setCnstDecl(CnstDecl CnstDecl) {
        this.CnstDecl=CnstDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CnstDecl!=null) CnstDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CnstDecl!=null) CnstDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CnstDecl!=null) CnstDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstGlobCnstDecl(\n");

        if(CnstDecl!=null)
            buffer.append(CnstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstGlobCnstDecl]");
        return buffer.toString();
    }
}
