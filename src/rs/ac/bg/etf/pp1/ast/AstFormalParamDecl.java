// generated with ast extension for cup
// version 0.8
// 2/5/2021 19:48:2


package src.rs.ac.bg.etf.pp1.ast;

public class AstFormalParamDecl extends FormalParamDecl {

    private Type Type;
    private String paramName;

    public AstFormalParamDecl (Type Type, String paramName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.paramName=paramName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName=paramName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstFormalParamDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+paramName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstFormalParamDecl]");
        return buffer.toString();
    }
}
