// generated with ast extension for cup
// version 0.8
// 12/2/2021 17:45:15


package rs.ac.bg.etf.pp1.ast;

public class AstIndexDesig extends Designator {

    private String name;
    private Expr Expr;

    public AstIndexDesig (String name, Expr Expr) {
        this.name=name;
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstIndexDesig(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstIndexDesig]");
        return buffer.toString();
    }
}