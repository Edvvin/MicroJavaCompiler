// generated with ast extension for cup
// version 0.8
// 14/2/2021 11:7:19


package rs.ac.bg.etf.pp1.ast;

public class AstVarDecl extends VarDecl {

    private Type Type;
    private VarAsgnList VarAsgnList;

    public AstVarDecl (Type Type, VarAsgnList VarAsgnList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarAsgnList=VarAsgnList;
        if(VarAsgnList!=null) VarAsgnList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(Type!=null) Type.accept(visitor);
        if(VarAsgnList!=null) VarAsgnList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarAsgnList!=null) VarAsgnList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarAsgnList!=null) VarAsgnList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstVarDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarAsgnList!=null)
            buffer.append(VarAsgnList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstVarDecl]");
        return buffer.toString();
    }
}
